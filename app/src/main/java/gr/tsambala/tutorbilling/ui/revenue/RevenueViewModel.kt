package gr.tsambala.tutorbilling.ui.revenue

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
class RevenueViewModel @Inject constructor(
    private val studentDao: StudentDao,
    private val lessonDao: LessonDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(RevenueUiState())
    val uiState: StateFlow<RevenueUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                studentDao.getAllActiveStudents(),
                lessonDao.getAllLessons()
            ) { students, lessons ->
                val studentMap = students.associateBy { it.id }

                val today = LocalDate.now()
                val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                val monthStart = today.withDayOfMonth(1)
                val monthEnd = today.withDayOfMonth(today.lengthOfMonth())

                val dayTotal = lessons.filter { it.date == today.toString() }
                    .sumOf { lesson ->
                        val student = studentMap[lesson.studentId] ?: return@sumOf 0.0
                        lesson.calculateFee(student)
                    }

                val weekTotal = lessons.filter { lesson ->
                    val date = LocalDate.parse(lesson.date)
                    !date.isBefore(weekStart) && !date.isAfter(weekEnd)
                }.sumOf { lesson ->
                    val student = studentMap[lesson.studentId] ?: return@sumOf 0.0
                    lesson.calculateFee(student)
                }

                val monthTotal = lessons.filter { lesson ->
                    val date = LocalDate.parse(lesson.date)
                    !date.isBefore(monthStart) && !date.isAfter(monthEnd)
                }.sumOf { lesson ->
                    val student = studentMap[lesson.studentId] ?: return@sumOf 0.0
                    lesson.calculateFee(student)
                }

                val (paidTotal, unpaidTotal) = lessons.filter { lesson ->
                    val date = LocalDate.parse(lesson.date)
                    !date.isBefore(monthStart) && !date.isAfter(monthEnd)
                }.partition { it.isPaid }.let { (paid, unpaid) ->
                    val paidSum = paid.sumOf { l ->
                        val s = studentMap[l.studentId] ?: return@sumOf 0.0
                        l.calculateFee(s)
                    }
                    val unpaidSum = unpaid.sumOf { l ->
                        val s = studentMap[l.studentId] ?: return@sumOf 0.0
                        l.calculateFee(s)
                    }
                    paidSum to unpaidSum
                }

                RevenueUiState(
                    dailyRevenue = dayTotal,
                    weeklyRevenue = weekTotal,
                    monthlyRevenue = monthTotal,
                    monthlyPaid = paidTotal,
                    monthlyUnpaid = unpaidTotal
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}

data class RevenueUiState(
    val dailyRevenue: Double = 0.0,
    val weeklyRevenue: Double = 0.0,
    val monthlyRevenue: Double = 0.0,
    val monthlyPaid: Double = 0.0,
    val monthlyUnpaid: Double = 0.0
)
