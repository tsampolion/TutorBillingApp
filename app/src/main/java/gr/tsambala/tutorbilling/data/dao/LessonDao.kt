package gr.tsambala.tutorbilling.data.dao

import androidx.room.*
import gr.tsambala.tutorbilling.data.model.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Insert
    suspend fun insert(lesson: Lesson): Long

    @Update
    suspend fun update(lesson: Lesson)

    @Delete
    suspend fun delete(lesson: Lesson)

    @Query("DELETE FROM lessons WHERE id = :lessonId")
    suspend fun deleteById(lessonId: Long)

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonById(lessonId: Long): Flow<Lesson?>

    @Query("SELECT * FROM lessons WHERE studentId = :studentId ORDER BY date DESC, startTime DESC")
    fun getLessonsByStudentId(studentId: Long): Flow<List<Lesson>>

    @Query("SELECT * FROM lessons ORDER BY date DESC, startTime DESC")
    fun getAllLessons(): Flow<List<Lesson>>

    @Query("SELECT * FROM lessons WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, startTime DESC")
    fun getLessonsInDateRange(startDate: String, endDate: String): Flow<List<Lesson>>
}