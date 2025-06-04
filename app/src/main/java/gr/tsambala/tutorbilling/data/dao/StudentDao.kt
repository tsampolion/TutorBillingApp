package gr.tsambala.tutorbilling.data.dao

import androidx.room.*
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student): Long

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM students WHERE id = :studentId")
    suspend fun deleteById(studentId: Long)

    @Query("SELECT * FROM students WHERE id = :studentId")
    fun getStudentById(studentId: Long): Flow<Student?>

    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<Student>>
}