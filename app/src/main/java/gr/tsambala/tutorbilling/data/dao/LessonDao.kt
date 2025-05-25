package gr.tsambala.tutorbilling.data.dao

import androidx.room.*
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate

/**
 * LessonDao defines all database operations for lessons.
 *
 * This is like the section of the librarian's manual dedicated to handling
 * lesson records - how to log new lessons, find past lessons, and generate
 * reports on teaching activity and earnings.
 */
@Dao
interface LessonDao {

    /**
     * Inserts a new lesson into the database.
     * Returns the auto-generated ID of the new lesson.
     *
     * Like adding a new entry to your teaching logbook.
     */
    @Insert
    suspend fun insertLesson(lesson: Lesson): Long

    /**
     * Updates an existing lesson's information.
     * Returns the number of rows updated.
     *
     * For when you need to correct the duration or add notes after the fact.
     */
    @Update
    suspend fun updateLesson(lesson: Lesson): Int

    /**
     * Deletes a lesson from the database.
     * Unlike students, we do hard delete lessons since they don't have
     * dependent data and mistakes can be re-entered.
     */
    @Delete
    suspend fun deleteLesson(lesson: Lesson)

    /**
     * Alternative delete by ID when you don't have the full lesson object.
     */
    @Query("DELETE FROM lessons WHERE id = :lessonId")
    suspend fun deleteLessonById(lessonId: Long)

    /**
     * Gets a single lesson by ID.
     * Useful for editing or viewing details of a specific lesson.
     */
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: Long): Lesson?

    /**
     * Gets all lessons for a specific student, ordered by date (newest first).
     * This powers the student detail screen showing their lesson history.
     *
     * Flow ensures the UI updates automatically when lessons are added/edited.
     */
    @Query("SELECT * FROM lessons WHERE studentId = :studentId ORDER BY date DESC, startTime DESC")
    fun getLessonsForStudent(studentId: Long): Flow<List<Lesson>>

    /**
     * Gets lessons for a student within a date range.
     * Useful for generating monthly reports or filtering by period.
     */
    @Query("""
        SELECT * FROM lessons 
        WHERE studentId = :studentId 
        AND date >= :startDate 
        AND date <= :endDate 
        ORDER BY date DESC, startTime DESC
    """)
    fun getLessonsForStudentInDateRange(
        studentId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<Lesson>>

    /**
     * Gets all lessons across all students for a date range.
     * Useful for calculating total earnings in a period.
     */
    @Query("""
        SELECT * FROM lessons 
        WHERE date >= :startDate 
        AND date <= :endDate 
        ORDER BY date DESC, startTime DESC
    """)
    fun getAllLessonsInDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<Lesson>>

    /**
     * Gets today's lessons across all students.
     * Helpful for a daily schedule view.
     */
    @Query("SELECT * FROM lessons WHERE date = :today ORDER BY startTime ASC")
    fun getTodaysLessons(today: LocalDate = LocalDate.now()): Flow<List<Lesson>>

    /**
     * Counts total lessons for a student.
     * Quick statistic without loading all lesson data.
     */
    @Query("SELECT COUNT(*) FROM lessons WHERE studentId = :studentId")
    suspend fun getLessonCountForStudent(studentId: Long): Int

    /**
     * Calculates total duration (in minutes) for a student in a date range.
     * Useful for time-based reporting.
     */
    @Query("""
        SELECT SUM(durationMinutes) 
        FROM lessons 
        WHERE studentId = :studentId 
        AND date >= :startDate 
        AND date <= :endDate
    """)
    suspend fun getTotalDurationForStudentInRange(
        studentId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): Int?

    /**
     * Gets the most recent lesson date for a student.
     * Helpful for showing "last lesson" info or detecting inactive students.
     */
    @Query("SELECT MAX(date) FROM lessons WHERE studentId = :studentId")
    suspend fun getLastLessonDateForStudent(studentId: Long): LocalDate?

    /**
     * Complex query that joins lessons with students to calculate earnings.
     * This is the heart of the financial calculations - it gets lessons
     * with their associated student data so we can calculate fees.
     *
     * The JOIN operation connects each lesson with its student's rate info.
     */
    @Transaction
    @Query("""
        SELECT l.* FROM lessons l
        INNER JOIN students s ON l.studentId = s.id
        WHERE s.isActive = 1
        AND l.date >= :startDate 
        AND l.date <= :endDate
        ORDER BY l.date DESC, l.startTime DESC
    """)
    fun getLessonsWithStudentsInDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<LessonWithStudent>>

    /**
     * Gets lessons with student data for a specific student.
     * More efficient than separate queries when you need both.
     */
    @Transaction
    @Query("""
        SELECT l.* FROM lessons l
        WHERE l.studentId = :studentId
        ORDER BY l.date DESC, l.startTime DESC
    """)
    fun getLessonsWithStudentForStudent(studentId: Long): Flow<List<LessonWithStudent>>
}

/**
 * Data class that combines a lesson with its associated student.
 * This is like having both the lesson logbook entry and the student's
 * rate card together, so we can calculate earnings.
 *
 * @Embedded tells Room to include all fields from both objects
 * in the query result.
 */
data class LessonWithStudent(
    @Embedded val lesson: Lesson,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "id"
    )
    val student: Student
) {
    /**
     * Convenience method to calculate the fee for this lesson.
     * Delegates to the lesson's calculation method with the student data.
     */
    fun calculateFee(): Double = lesson.calculateFee(student)
}