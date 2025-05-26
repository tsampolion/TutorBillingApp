package gr.tsambala.tutorbilling.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.dao.LessonDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val studentDao: StudentDao,
    private val lessonDao: LessonDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadStudentsWithEarnings()
    }

    private fun loadStudentsWithEarnings() {
        viewModelScope.launch {
            combine(
                studentDao.getAllStudents(),
                lessonDao.getAllLessons()
            ) { students, lessons ->
                val today = LocalDate.now()
                val weekFields = WeekFields.of(Locale.getDefault())
                val currentWeek = today.get(weekFields.weekOfWeekBasedYear())
                val currentMonth = today.monthValue
                val currentYear = today.year

                students.map { student ->
                    val studentLessons = lessons.filter { it.studentId == student.id }

                    val weekEarnings = studentLessons
                        .filter { lesson ->
                            val lessonDate = LocalDate.parse(lesson.date)
                            lessonDate.year == currentYear &&
                                    lessonDate.get(weekFields.weekOfWeekBasedYear()) == currentWeek
                        }
                        .sumOf { lesson ->
                            // Calculate fee based on student rate
                            when (student.rateType) {
                                "hourly" -> (lesson.durationMinutes / 60.0) * student.rate
                                else -> student.rate
                            }
                        }

                    val monthEarnings = studentLessons
                        .filter { lesson ->
                            val lessonDate = LocalDate.parse(lesson.date)
                            lessonDate.year == currentYear &&
                                    lessonDate.monthValue == currentMonth
                        }
                        .sumOf { lesson ->
                            // Calculate fee based on student rate
                            when (student.rateType) {
                                "hourly" -> (lesson.durationMinutes / 60.0) * student.rate
                                else -> student.rate
                            }
                        }

                    StudentWithEarnings(
                        student = student,
                        weekEarnings = weekEarnings,
                        monthEarnings = monthEarnings
                    )
                }
            }.collect { studentsWithEarnings ->
                _uiState.update { it.copy(students = studentsWithEarnings) }
            }
        }
    }

    fun deleteStudent(studentId: Long) {
        viewModelScope.launch {
            studentDao.deleteById(studentId)
        }
    }
}

data class HomeUiState(
    val students: List<StudentWithEarnings> = emptyList()
)

data class StudentWithEarnings(
    val student: Student,
    val weekEarnings: Double,
    val monthEarnings: Double
)