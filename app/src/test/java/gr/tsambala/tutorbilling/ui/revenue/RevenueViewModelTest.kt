package gr.tsambala.tutorbilling.ui.revenue

import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.database.LessonWithStudent
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate

@RunWith(RobolectricTestRunner::class)
class RevenueViewModelTest {

    private val studentFlow = MutableStateFlow<List<Student>>(emptyList())
    private val lessonFlow = MutableStateFlow<List<Lesson>>(emptyList())

    private val studentDao = FakeStudentDao(studentFlow)
    private val lessonDao = FakeLessonDao(lessonFlow)

    @Test
    fun debtsCalculatedAndCleared() = runTest {
        val s1 = Student(id = 1, name = "Alice", surname = "", parentMobile = "", className = "A", rate = 20.0)
        val s2 = Student(id = 2, name = "Bob", surname = "", parentMobile = "", className = "B", rate = 15.0)
        studentFlow.value = listOf(s1, s2)

        val today = LocalDate.now().toString()
        lessonFlow.value = listOf(
            Lesson(id = 1, studentId = 1, date = today, startTime = "10:00", durationMinutes = 60, isPaid = false),
            Lesson(id = 2, studentId = 1, date = today, startTime = "11:00", durationMinutes = 60, isPaid = true),
            Lesson(id = 3, studentId = 2, date = today, startTime = "12:00", durationMinutes = 120, isPaid = false)
        )

        val vm = RevenueViewModel(studentDao, lessonDao)
        advanceUntilIdle()

        val debts = vm.uiState.value.debts
        assertEquals(2, debts.size)
        assertEquals(20.0, debts[0].amount, 0.0)
        assertEquals(30.0, debts[1].amount, 0.0)

        vm.markLessonsPaid(1)
        advanceUntilIdle()
        val updated = vm.uiState.value.debts
        assertEquals(1, updated.size)
        assertEquals(2L, updated[0].student.id)
    }

    class FakeStudentDao(private val flow: MutableStateFlow<List<Student>>) : StudentDao {
        override suspend fun insert(student: Student): Long { flow.value += student; return student.id }
        override suspend fun update(student: Student) {}
        override suspend fun delete(student: Student) {}
        override suspend fun softDeleteStudent(studentId: Long) {}
        override fun getStudentById(studentId: Long): Flow<Student?> = flow.map { list -> list.find { it.id == studentId } }
        override fun getAllActiveStudents(): Flow<List<Student>> = flow.asStateFlow()
        override fun getArchivedStudents(): Flow<List<Student>> = flowOf(emptyList())
        override suspend fun restoreStudent(studentId: Long) {}
        override fun getStudentByIdAny(studentId: Long): Flow<Student?> = flow.map { list -> list.find { it.id == studentId } }
        override suspend fun getActiveStudentCount(): Int = flow.value.size
        override suspend fun classNameExists(name: String): Int = flow.value.count { it.className.equals(name, true) }
    }

    class FakeLessonDao(private val flow: MutableStateFlow<List<Lesson>>) : LessonDao {
        override suspend fun insert(lesson: Lesson): Long { flow.value += lesson; return lesson.id }
        override suspend fun update(lesson: Lesson) {}
        override suspend fun delete(lesson: Lesson) {}
        override suspend fun deleteById(lessonId: Long) {}
        override fun getLessonById(lessonId: Long): Flow<Lesson?> = flow.map { it.find { l -> l.id == lessonId } }
        override fun getLessonsByStudentId(studentId: Long): Flow<List<Lesson>> = flow.map { list -> list.filter { it.studentId == studentId } }
        override fun getAllLessons(): Flow<List<Lesson>> = flow.asStateFlow()
        override fun getLessonsInDateRange(startDate: String, endDate: String): Flow<List<Lesson>> = flowOf(emptyList())
        override fun getLessonsByStudentAndDateRange(studentId: Long, startDate: String, endDate: String): Flow<List<Lesson>> = flowOf(emptyList())
        override fun getUnpaidLessonsByStudentAndDateRange(studentId: Long, startDate: String, endDate: String): Flow<List<Lesson>> = flowOf(emptyList())
        override fun getUnpaidLessonsInDateRange(startDate: String, endDate: String): Flow<List<Lesson>> = flowOf(emptyList())
        override suspend fun updatePaidStatus(ids: List<Long>, paid: Boolean) {
            flow.value = flow.value.map { if (it.id in ids) it.copy(isPaid = paid) else it }
        }
        override fun getLessonsWithStudents(): Flow<List<LessonWithStudent>> = flowOf(emptyList())
        override fun getLessonsWithStudentsByStudent(studentId: Long): Flow<List<LessonWithStudent>> = flowOf(emptyList())
        override fun getLessonsWithStudentsInDateRange(startDate: String, endDate: String): Flow<List<LessonWithStudent>> = flowOf(emptyList())
        override fun getLessonsWithStudentsByStudentAndDateRange(studentId: Long, startDate: String, endDate: String): Flow<List<LessonWithStudent>> = flowOf(emptyList())
    }
}
