package gr.tsambala.tutorbilling.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.model.calculateFee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HomeMenuViewModel @Inject constructor(
    private val studentDao: StudentDao,
    private val lessonDao: LessonDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeMenuUiState())
    val uiState: StateFlow<HomeMenuUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                studentDao.getAllActiveStudents(),
                lessonDao.getAllLessons()
            ) { students, lessons ->
                val classCount = 0

                val today = LocalDate.now()
                val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                val monthStart = today.withDayOfMonth(1)
                val monthEnd = today.withDayOfMonth(today.lengthOfMonth())

                val weekTotal = lessons.filter { lesson ->
                    val date = LocalDate.parse(lesson.date)
                    !date.isBefore(weekStart) && !date.isAfter(weekEnd)
                }.sumOf { lesson ->
                    val student = students.firstOrNull { it.id == lesson.studentId }
                    student?.let { lesson.calculateFee(it) } ?: 0.0
                }

                val monthTotal = lessons.filter { lesson ->
                    val date = LocalDate.parse(lesson.date)
                    !date.isBefore(monthStart) && !date.isAfter(monthEnd)
                }.sumOf { lesson ->
                    val student = students.firstOrNull { it.id == lesson.studentId }
                    student?.let { lesson.calculateFee(it) } ?: 0.0
                }
                HomeMenuUiState(
                    studentCount = students.size,
                    classCount = classCount,
                    lessonCount = lessons.size,
                    weekRevenue = weekTotal,
                    monthRevenue = monthTotal
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}

data class HomeMenuUiState(
    val studentCount: Int = 0,
    val classCount: Int = 0,
    val lessonCount: Int = 0,
    val weekRevenue: Double = 0.0,
    val monthRevenue: Double = 0.0
)
