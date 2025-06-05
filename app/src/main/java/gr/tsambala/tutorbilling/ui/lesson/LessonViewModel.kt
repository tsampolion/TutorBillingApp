package gr.tsambala.tutorbilling.ui.lesson

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.model.RateTypes
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val lessonDao: LessonDao,
    private val studentDao: StudentDao
) : ViewModel() {

    private val studentId: String? = savedStateHandle.get<String>("studentId")
    private val lessonId: String? = savedStateHandle.get<String>("lessonId")

    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    init {
        loadStudentInfo()
        if (lessonId != null && lessonId != "new") {
            loadLesson()
        } else {
            // Set default values for new lesson
            _uiState.update {
                it.copy(
                    date = LocalDate.now().toString(),
                    startTime = LocalTime.now().withSecond(0).withNano(0).toString()
                )
            }
        }
    }

    private fun loadStudentInfo() {
        viewModelScope.launch {
            studentId?.toLongOrNull()?.let { id ->
                studentDao.getStudentById(id).collect { student ->
                    student?.let { s ->
                        _uiState.update { state ->
                            state.copy(
                                studentName = s.name,
                                studentRateType = s.rateType,
                                studentRate = s.rate
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadLesson() {
        viewModelScope.launch {
            lessonId?.toLongOrNull()?.let { id ->
                lessonDao.getLessonById(id).collect { lesson ->
                    lesson?.let { l ->
                        _uiState.update { state ->
                            state.copy(
                                date = l.date,
                                startTime = l.startTime,
                                durationMinutes = l.durationMinutes.toString(),
                                notes = l.notes ?: "",
                                isEditMode = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateDate(date: String) {
        _uiState.update { it.copy(date = date) }
    }

    fun updateStartTime(time: String) {
        _uiState.update { it.copy(startTime = time) }
    }

    fun updateDuration(duration: String) {
        val digits = duration.filter { it.isDigit() }
        val sanitized = digits.toIntOrNull()?.takeIf { it > 0 }?.toString() ?: ""
        _uiState.update { it.copy(durationMinutes = sanitized) }
    }

    fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    private fun isValidDate(value: String): Boolean = try {
        LocalDate.parse(value)
        true
    } catch (_: Exception) { false }

    private fun isValidTime(value: String): Boolean = try {
        LocalTime.parse(value)
        true
    } catch (_: Exception) { false }

    fun isFormValid(): Boolean {
        val state = _uiState.value
        val duration = state.durationMinutes.toIntOrNull() ?: 0
        return duration > 0 && isValidDate(state.date) && isValidTime(state.startTime)
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun saveLesson() {
        viewModelScope.launch {
            val state = _uiState.value
            val duration = state.durationMinutes.toIntOrNull() ?: 0
            if (!isFormValid()) return@launch

            studentId?.toLongOrNull()?.let { sId ->
                if (lessonId == "new") {
                    val lesson = Lesson(
                        studentId = sId,
                        date = state.date,
                        startTime = state.startTime,
                        durationMinutes = duration,
                        notes = state.notes.ifBlank { null }
                    )
                    lessonDao.insert(lesson)
                } else {
                    lessonId?.toLongOrNull()?.let { lId ->
                        val lesson = Lesson(
                            id = lId,
                            studentId = sId,
                            date = state.date,
                            startTime = state.startTime,
                            durationMinutes = duration,
                            notes = state.notes.ifBlank { null }
                        )
                        lessonDao.update(lesson)
                    }
                }
            }

            _uiState.update { it.copy(isEditMode = false) }
        }
    }

    fun deleteLesson(onDeleted: () -> Unit) {
        viewModelScope.launch {
            lessonId?.toLongOrNull()?.let { id ->
                lessonDao.deleteById(id)
                onDeleted()
            }
        }
    }

    fun calculateFee(): Double {
        val state = _uiState.value
        val duration = state.durationMinutes.toIntOrNull() ?: 0

        return when (state.studentRateType) {
            RateTypes.HOURLY -> (duration / 60.0) * state.studentRate
            RateTypes.PER_LESSON -> state.studentRate
            else -> state.studentRate
        }
    }
}

data class LessonUiState(
    val date: String = "",
    val startTime: String = "",
    val durationMinutes: String = "",
    val notes: String = "",
    val studentName: String = "",
    val studentRateType: String = RateTypes.HOURLY,
    val studentRate: Double = 0.0,
    val isEditMode: Boolean = true
)