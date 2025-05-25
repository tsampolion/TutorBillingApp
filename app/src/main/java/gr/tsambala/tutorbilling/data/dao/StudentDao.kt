package gr.tsambala.tutorbilling.data.dao

import androidx.room.*
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.Flow
import java.time.Instant

/**
 * StudentDao (Data Access Object) defines all database operations for students.
 *
 * Think of this as a librarian's instruction manual - it contains all the
 * approved ways to add, find, update, and remove student records from our database.
 *
 * The @Dao annotation tells Room that this interface contains database operations.
 * Room will generate the actual implementation code for us at compile time.
 */
@Dao
interface StudentDao {

    /**
     * Inserts a new student into the database.
     * Returns the auto-generated ID of the new student.
     *
     * @Insert is like filing a new student card in the cabinet.
     * The suspend keyword means this operation happens asynchronously,
     * preventing the UI from freezing while we save data.
     */
    @Insert
    suspend fun insertStudent(student: Student): Long

    /**
     * Updates an existing student's information.
     * Returns the number of rows updated (should be 1 if successful).
     *
     * @Update is like pulling out a student's card, making changes,
     * and putting it back in the same spot.
     */
    @Update
    suspend fun updateStudent(student: Student): Int

    /**
     * Soft deletes a student by marking them as inactive.
     * We don't actually remove the data - just hide it from normal views.
     * This preserves historical lesson data.
     *
     * @Query lets us write custom SQL when the built-in operations aren't enough.
     */
    @Query("UPDATE students SET isActive = 0, updatedAt = :timestamp WHERE id = :studentId")
    suspend fun softDeleteStudent(studentId: Long, timestamp: Instant)

    /**
     * Gets a single student by their ID.
     * Returns null if no student found with that ID.
     *
     * This is like asking the librarian to fetch a specific student's file.
     */
    @Query("SELECT * FROM students WHERE id = :studentId AND isActive = 1")
    suspend fun getStudentById(studentId: Long): Student?

    /**
     * Gets all active students as a Flow.
     * Flow is like a subscription - whenever the data changes,
     * observers are automatically notified with the new data.
     *
     * This powers our home screen list, keeping it always up-to-date.
     */
    @Query("SELECT * FROM students WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveStudents(): Flow<List<Student>>

    /**
     * Gets all students including inactive ones.
     * Useful for administrative views or data recovery.
     */
    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudentsIncludingInactive(): Flow<List<Student>>

    /**
     * Searches for students by name.
     * The % symbols in SQL are wildcards, so searching for "John"
     * would find "John Smith", "Johnny", and "Johnson".
     */
    @Query("SELECT * FROM students WHERE isActive = 1 AND name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchStudentsByName(searchQuery: String): Flow<List<Student>>

    /**
     * Counts the total number of active students.
     * Useful for statistics or limiting features in a free version.
     */
    @Query("SELECT COUNT(*) FROM students WHERE isActive = 1")
    suspend fun getActiveStudentCount(): Int

    /**
     * Checks if a student has any lessons.
     * Important for determining if we can safely delete a student
     * or if we should warn about losing lesson data.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM lessons WHERE studentId = :studentId LIMIT 1)")
    suspend fun studentHasLessons(studentId: Long): Boolean

    /**
     * Gets students with their lesson count.
     * This is a more complex query that joins data from two tables.
     * We'll use this for showing lesson counts on the home screen.
     */
    @Query("""
        SELECT s.*, COUNT(l.id) as lessonCount 
        FROM students s 
        LEFT JOIN lessons l ON s.id = l.studentId 
        WHERE s.isActive = 1 
        GROUP BY s.id 
        ORDER BY s.name ASC
    """)
    fun getStudentsWithLessonCount(): Flow<List<StudentWithLessonCount>>
}

/**
 * Data class to hold the result of the student + lesson count query.
 * This is like a summary card that shows both student info and
 * how many lessons they've had.
 */
data class StudentWithLessonCount(
    @Embedded val student: Student,
    val lessonCount: Int
)