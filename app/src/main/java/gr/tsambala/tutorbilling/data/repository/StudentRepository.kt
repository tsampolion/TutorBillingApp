package gr.tsambala.tutorbilling.data.repository

import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(
    private val studentDao: StudentDao
) {
    fun getStudentById(studentId: Long): Flow<Student?> = studentDao.getStudentById(studentId)

    suspend fun insertStudent(student: Student): Long = studentDao.insert(student)

    suspend fun updateStudent(student: Student) = studentDao.update(student)

    suspend fun deleteStudent(student: Student) {
        studentDao.softDeleteStudent(student.id)
    }
}
