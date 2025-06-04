package gr.tsambala.tutorbilling.data.repository

import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.database.LessonWithStudent
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TutorBillingRepository is the single source of truth for all data operations.
 *
 * This class sits between the UI layer (ViewModels) and the data layer (DAOs),
 * providing a clean API that hides the complexity of data operations.
 * It offers methods to add, update, delete, and query students and lessons,
 * as well as utility functions for financial calculations.
 *
 * @Inject tells Hilt to automatically provide the DAOs when creating this repository.
 * @Singleton ensures a single instance is used throughout the app.
 */
@Singleton
class TutorBillingRepository @Inject constructor(
    private val studentDao: StudentDao,
    private val lessonDao: LessonDao
) {

    // ===== Student Operations =====

    /**
     * Adds a new student to the database.
     * Validates the student data before saving.
     *
     * @return The ID of the newly created student
     * @throws IllegalArgumentException if student data is invalid
     */
    suspend fun addStudent(student: Student): Long {
        require(student.name.isNotBlank()) { "Student name cannot be empty" }
        require(student.hourlyRate != null || student.ratePerLesson != null) {
            "Student must have either an hourly rate or a per-lesson rate"
        }
        return studentDao.insertStudent(student)
    }

    /**
     * Updates an existing student's information.
     * Automatically updates the 'updatedAt' timestamp.
     */
    suspend fun updateStudent(student: Student) {
        val now = Instant.now()
        val updatedStudent = student.copy(updatedAt = now)
        studentDao.updateStudent(updatedStudent)
    }

    /**
     * Soft deletes a student by setting 'isActive' to false.
     */
    suspend fun deleteStudent(studentId: Long) {
        val existing = studentDao.getStudentById(studentId) ?: return
        val now = Instant.now()
        val softDeleted = existing.copy(isActive = false, updatedAt = now)
        studentDao.updateStudent(softDeleted)
    }

    /**
     * Gets a single student by ID.
     */
    suspend fun getStudent(studentId: Long): Student? {
        return studentDao.getStudentById(studentId)
    }

    /**
     * Gets all active students as a Flow.
     */
    fun getAllActiveStudents(): Flow<List<Student>> {
        return studentDao.getAllActiveStudents()
    }

    // ===== Lesson Operations =====

    /**
     * Adds a new lesson to the database.
     * Validates that the student exists and is active.
     *
     * @return The ID of the newly created lesson
     * @throws IllegalArgumentException if data is invalid
     * @throws IllegalStateException if student doesn't exist or is inactive
     */
    suspend fun addLesson(lesson: Lesson): Long {
        require(lesson.durationMinutes > 0) { "Lesson duration must be positive" }
        val student = studentDao.getStudentById(lesson.studentId)
        checkNotNull(student) { "Cannot add lesson for a non-existent student" }
        check(student.isActive) { "Cannot add lesson for an inactive student" }
        return lessonDao.insertLesson(lesson)
    }

    /**
     * Updates an existing lesson.
     * Automatically updates the 'updatedAt' timestamp.
     */
    suspend fun updateLesson(lesson: Lesson) {
        val now = Instant.now()
        val updatedLesson = lesson.copy(updatedAt = now)
        lessonDao.updateLesson(updatedLesson)
    }

    /**
     * Deletes a lesson permanently by its ID.
     */
    suspend fun deleteLesson(lessonId: Long) {
        lessonDao.deleteLessonById(lessonId)
    }

    /**
     * Gets all lessons for a specific student as a Flow.
     */
    fun getLessonsForStudent(studentId: Long): Flow<List<Lesson>> {
        return lessonDao.getLessonsForStudent(studentId)
    }

    /**
     * Gets lessons with full student data for a specific student.
     */
    fun getLessonsWithStudentData(studentId: Long): Flow<List<LessonWithStudent>> {
        return lessonDao.getLessonsWithStudentForStudent(studentId)
    }

    // ===== Financial Calculations =====

    /**
     * Holds weekly/monthly totals (plus lesson count) for a given student.
     */
    data class StudentFinancialSummary(
        val student: Student,
        val weekTotal: Double,
        val monthTotal: Double,
        val lessonCount: Int
    )

    /**
     * Emits a list of [StudentFinancialSummary], one per active student.
     */
    fun getStudentFinancialSummaries(): Flow<List<StudentFinancialSummary>> {
        val today = LocalDate.now()
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val monthStart = today.withDayOfMonth(1)
        val monthEnd = today.withDayOfMonth(today.lengthOfMonth())

        return combine(
            studentDao.getAllActiveStudents(),
            lessonDao.getLessonsWithStudentsInDateRange(weekStart, weekEnd),
            lessonDao.getLessonsWithStudentsInDateRange(monthStart, monthEnd)
        ) { students, weekLessons, monthLessons ->
            students.map { student ->
                val studentWeekLessons = weekLessons.filter { it.student.id == student.id }
                val studentMonthLessons = monthLessons.filter { it.student.id == student.id }
                val weekTotal = studentWeekLessons.sumOf { it.calculateFee() }
                val monthTotal = studentMonthLessons.sumOf { it.calculateFee() }
                StudentFinancialSummary(
                    student = student,
                    weekTotal = weekTotal,
                    monthTotal = monthTotal,
                    lessonCount = studentMonthLessons.size
                )
            }
        }
    }

    /**
     * Gets total earnings across all students for a given date range.
     */
    fun getTotalEarningsForDateRange(startDate: LocalDate, endDate: LocalDate): Flow<Double> {
        return lessonDao.getLessonsWithStudentsInDateRange(startDate, endDate)
            .map { lessonList -> lessonList.sumOf { it.calculateFee() } }
    }

    /**
     * Detailed report structure for a single student.
     */
    data class StudentDetailedReport(
        val student: Student,
        val totalLessons: Int,
        val totalHours: Double,
        val totalEarnings: Double,
        val averageLessonDuration: Double,
        val lastLessonDate: LocalDate?
    )

    /**
     * Generates a detailed report for a single student.
     */
    suspend fun getStudentDetailedReport(studentId: Long): StudentDetailedReport? {
        val student = studentDao.getStudentById(studentId) ?: return null
        val lessons: List<Lesson> = lessonDao.getLessonsForStudent(studentId).first()
        val totalMinutes = lessons.sumOf { it.durationMinutes }
        val totalHours = totalMinutes / 60.0
        val totalEarnings = lessons.sumOf { it.calculateFee(student) }
        val averageDuration = if (lessons.isNotEmpty()) totalMinutes.toDouble() / lessons.size else 0.0
        val lastLessonDate = lessons.maxOfOrNull { it.date }
        return StudentDetailedReport(
            student = student,
            totalLessons = lessons.size,
            totalHours = totalHours,
            totalEarnings = totalEarnings,
            averageLessonDuration = averageDuration,
