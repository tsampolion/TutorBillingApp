package gr.tsambala.tutorbilling.ui.invoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.database.LessonWithStudent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val lessonDao: LessonDao,
    private val studentDao: StudentDao
) : ViewModel() {

    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1))
    private val _endDate = MutableStateFlow(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()))
    val startDate: StateFlow<LocalDate> = _startDate.asStateFlow()
    val endDate: StateFlow<LocalDate> = _endDate.asStateFlow()

    private val _lessons = MutableStateFlow<List<LessonWithStudent>>(emptyList())
    val lessons: StateFlow<List<LessonWithStudent>> = _lessons.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_startDate, _endDate) { start, end -> start to end }
                .collect { (start, end) ->
                    combine(
                        lessonDao.getUnpaidLessonsInDateRange(start.toString(), end.toString()),
                        studentDao.getAllActiveStudents()
                    ) { lessons, students ->
                        val map = students.associateBy { it.id }
                        lessons.mapNotNull { l -> map[l.studentId]?.let { s -> LessonWithStudent(l, s) } }
                    }.collect { list ->
                        _lessons.value = list
                    }
                }
        }
    }

    fun updateStartDate(date: LocalDate) { _startDate.value = date }
    fun updateEndDate(date: LocalDate) { _endDate.value = date }

    fun markAsPaid(ids: List<Long>) {
        viewModelScope.launch { lessonDao.updatePaidStatus(ids, true) }
    }
}
