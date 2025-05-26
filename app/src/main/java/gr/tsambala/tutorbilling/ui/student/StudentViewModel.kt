package gr.tsambala.tutorbilling.ui.student

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Lesson
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
class StudentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studentDao: StudentDao,
    private val lessonDao: LessonDao
) : ViewModel() {

    private val studentId: String? = savedStateHandle.get<String>("studentId")

    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    init {
        if (studentId != null && studentId != "new") {
            loadStudent()
            loadLessons()
        }
    }

    private fun loadStudent() {
        viewModelScope.launch {
            studentId?.toLongOrNull()?.let { id ->
                studentDao.getStudentById(id).collect { student ->
                    student?.let {
                        _uiState.update { state ->
                            state.copy(
                                name = it.name,
                                rateType = it.rateType,
                                rate = it.rate.toString(),
                                isEditMode = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadLessons() {
        viewModelScope.launch {
            studentId?.toLongOrNull()?.let { id ->
                lessonDao.getLessonsByStudentId(id).collect { lessons ->
                    val today = LocalDate.now()
                    val weekFields = WeekFields.of(Locale.getDefault())
                    val currentWeek = today.get(weekFields.weekOfWeekBasedYear())
                    val currentMonth = today.monthValue
                    val currentYear = today.year

                    val weekEarnings = lessons
                        .filter { lesson ->
                            val lessonDate = LocalDate.parse(lesson.date)
                            lessonDate.year == currentYear &&
                                    lessonDate.get(weekFields.weekOfWeekBasedYear()) == currentWeek
                        }
                        .sumOf { it.calculateFee() }

                    val monthEarnings = lessons
                        .filter { lesson ->
                            val lessonDate = LocalDate.parse(lesson.date)
                            lessonDate.year == currentYear &&
                                    lessonDate.monthValue == currentMonth
                        }
                        .sumOf { it.calculateFee() }

                    val totalEarnings = lessons.sumOf { it.calculateFee() }

                    _uiState.update { state ->
                        state.copy(
                            lessons = lessons,
                            weekEarnings = weekEarnings,
                            monthEarnings = monthEarnings,
                            totalEarnings = totalEarnings
                        )
                    }
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateRateType(rateType: String) {
        _uiState.update { it.copy(rateType = rateType) }
    }

    fun updateRate(rate: String) {
        _uiState.update { it.copy(rate = rate) }
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun saveStudent() {
        viewModelScope.launch {
            val state = _uiState.value
            val rate = state.rate.toDoubleOrNull() ?: 0.0

            if (studentId == "new") {
                val student = Student(
                    name = state.name,
                    rateType = state.rateType,
                    rate = rate
                )
                studentDao.insert(student)
            } else {
                studentId?.toLongOrNull()?.let { id ->
                    val student = Student(
                        id = id,
                        name = state.name,
                        rateType = state.rateType,
                        rate = rate
                    )
                    studentDao.update(student)
                }
            }

            _uiState.update { it.copy(isEditMode = false) }
        }
    }

    fun deleteStudent(onDeleted: () -> Unit) {
        viewModelScope.launch {
            studentId?.toLongOrNull()?.let { id ->
                studentDao.deleteById(id)
                onDeleted()
            }
        }
    }

    fun deleteLesson(lessonId: Long) {
        viewModelScope.launch {
            lessonDao.deleteById(lessonId)
        }
    }
}

data class StudentUiState(
    val name: String = "",
    val rateType: String = "hourly",
    val rate: String = "",
    val lessons: List<Lesson> = emptyList(),
    val weekEarnings: Double = 0.0,
    val monthEarnings: Double = 0.0,
    val totalEarnings: Double = 0.0,
    val isEditMode: Boolean = true
)