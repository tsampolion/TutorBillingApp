package gr.tsambala.tutorbilling.data.repository

import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.dao.LessonWithStudent
import gr.tsambala.tutorbilling.data.dao.StudentWithLessonCount
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOf
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
 * providing a clean API that hides the complexity of data operations. It's like
 * having a skilled assistant who knows exactly where to find information and
 * how to present it in the most useful way.
 *
 * Benefits of the Repository pattern:
 * 1. Single source of truth - all data flows through here
 * 2. Abstraction - ViewModels don't need to know about DAOs or database details
 * 3. Business logic - complex calculations and data combinations happen here
 * 4. Testability - easy to mock for testing
 * 5. Future flexibility - could add caching, API calls, etc. without changing ViewModels
 *
 * @Inject tells Hilt to automatically provide the DAOs when creating this repository
 * @Singleton ensures we only have one repository instance in the app
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
        require(student.hasValidRate()) { "Student must have either hourly or per-lesson rate" }

        return studentDao.insertStudent(student)
    }

    /**
     * Updates an existing student's information.
     * Automatically updates the timestamp.
     */
    suspend fun updateStudent(student: Student) {
        val updatedStudent = student.copy(updatedAt = Instant.now())
        studentDao.updateStudent(updatedStudent)
    }

    /**
     * Soft deletes a student (marks as inactive).
     * Preserves the data for historical records.
     */
    suspend fun deleteStudent(studentId: Long) {
        studentDao.softDeleteStudent(studentId, Instant.now())
    }

    /**
     * Gets a single student by ID.
     */
    suspend fun getStudent(studentId: Long): Student? {
        return studentDao.getStudentById(studentId)
    }

    /**
     * Gets all active students as a Flow.
     * The UI will automatically update when students are added/edited/deleted.
     */
    fun getAllActiveStudents(): Flow<List<Student>> {
        return studentDao.getAllActiveStudents()
    }

    /**
     * Gets students with their lesson count.
     * Useful for showing statistics on the home screen.
     */
    fun getStudentsWithLessonCount(): Flow<List<StudentWithLessonCount>> {
        return studentDao.getStudentsWithLessonCount()
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
        checkNotNull(student) { "Cannot add lesson for non-existent student" }
        check(student.isActive) { "Cannot add lesson for inactive student" }

        return lessonDao.insertLesson(lesson)
    }

    /**
     * Updates an existing lesson.
     * Automatically updates the timestamp.
     */
    suspend fun updateLesson(lesson: Lesson) {
        val updatedLesson = lesson.copy(updatedAt = Instant.now())
        lessonDao.updateLesson(updatedLesson)
    }

    /**
     * Deletes a lesson permanently.
     */
    suspend fun deleteLesson(lessonId: Long) {
        lessonDao.deleteLessonById(lessonId)
    }

    /**
     * Gets lessons for a specific student.
     */
    fun getLessonsForStudent(studentId: Long): Flow<List<Lesson>> {
        return lessonDao.getLessonsForStudent(studentId)
    }

    /**
     * Gets lessons with full student data for a specific student.
     * This is more efficient than loading them separately when you need both.
     */
    fun getLessonsWithStudentData(studentId: Long): Flow<List<LessonWithStudent>> {
        return lessonDao.getLessonsWithStudentForStudent(studentId)
    }

    // ===== Financial Calculations =====

    /**
     * Data class to hold financial summary information.
     * This bundles all the calculations needed for the home screen.
     */
    data class StudentFinancialSummary(
        val student: Student,
        val weekTotal: Double,
        val monthTotal: Double,
        val lessonCount: Int
    )

    /**
     * Gets financial summaries for all active students.
     * This is the main data source for the home screen, showing each student
     * with their weekly and monthly earnings.
     *
     * This method demonstrates the power of the repository pattern - it combines
     * data from multiple sources and performs complex calculations, but presents
     * a simple interface to the ViewModel.
     */
    fun getStudentFinancialSummaries(): Flow<List<StudentFinancialSummary>> {
        // Get the date ranges for calculations
        val today = LocalDate.now()
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val monthStart = today.withDayOfMonth(1)
        val monthEnd = today.withDayOfMonth(today.lengthOfMonth())

        // Combine multiple data streams to create summaries
        return combine(
            studentDao.getAllActiveStudents(),
            lessonDao.getLessonsWithStudentsInDateRange(weekStart, weekEnd),
            lessonDao.getLessonsWithStudentsInDateRange(monthStart, monthEnd)
        ) { students, weekLessons, monthLessons ->
            students.map { student ->
                // Filter lessons for this student
                val studentWeekLessons = weekLessons.filter { it.student.id == student.id }
                val studentMonthLessons = monthLessons.filter { it.student.id == student.id }

                // Calculate totals
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
     * Gets total earnings for a date range across all students.
     * Useful for reports and analytics.
     */
    fun getTotalEarningsForDateRange(startDate: LocalDate, endDate: LocalDate): Flow<Double> {
        return lessonDao.getLessonsWithStudentsInDateRange(startDate, endDate)
            .map { lessons -> lessons.sumOf { it.calculateFee() } }
    }

    /**
     * Gets detailed financial report for a specific student.
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
     * Generates a detailed report for a student.
     * This kind of complex data aggregation is perfect for the repository layer.
     */
    suspend fun getStudentDetailedReport(studentId: Long): StudentDetailedReport? {
        val student = studentDao.getStudentById(studentId) ?: return null

        // Get the first emission from the flow
        val lessons = lessonDao.getLessonsForStudent(studentId).first()

        val totalMinutes = lessons.sumOf { it.durationMinutes }
        val totalHours = totalMinutes / 60.0
        val totalEarnings = lessons.sumOf { it.calculateFee(student) }
        val averageDuration = if (lessons.isNotEmpty()) {
            totalMinutes.toDouble() / lessons.size
        } else 0.0

        return StudentDetailedReport(
            student = student,
            totalLessons = lessons.size,
            totalHours = totalHours,
            totalEarnings = totalEarnings,
            averageLessonDuration = averageDuration,
            lastLessonDate = lessons.maxOfOrNull { it.date }
        )
    }

    // ===== Utility Functions =====

    /**
     * Gets a single lesson by ID.
     */
    suspend fun getLessonById(lessonId: Long): Lesson? {
        return lessonDao.getLessonById(lessonId)
    }

    /**
     * Checks if a student can be safely deleted.
     * A student can be deleted if they have no lessons.
     */
    suspend fun canDeleteStudent(studentId: Long): Boolean {
        return !studentDao.studentHasLessons(studentId)
    }

    /**
     * Searches for students by name.
     */
    fun searchStudents(query: String): Flow<List<Student>> {
        return if (query.isBlank()) {
            studentDao.getAllActiveStudents()
        } else {
            studentDao.searchStudentsByName(query.trim())
        }
    }
}