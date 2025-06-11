package gr.tsambala.tutorbilling.ui.lesson

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val lessonDao: LessonDao,
    private val studentDao: StudentDao
) : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    private val studentId: Long? = savedStateHandle.get<Long>("studentId")
    private val lessonId: Long? = savedStateHandle.get<Long>("lessonId")

    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    // Navigation callback
    private var onNavigateBack: (() -> Unit)? = null

    fun setNavigationCallback(callback: () -> Unit) {
        onNavigateBack = callback
    }

    init {
        loadStudentInfo()
        if (lessonId != null && lessonId != 0L) {
            loadLesson()
        } else {
            // Set default values for new lesson
            _uiState.update {
                it.copy(
                    date = LocalDate.now().format(dateFormatter),
                    startTime = LocalTime.now().withSecond(0).withNano(0).format(timeFormatter)
                )
            }
        }
    }

    private fun loadStudentInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = studentId?.takeIf { it != 0L }
            if (id != null) {
                studentDao.getStudentById(id).collect { student ->
                    student?.let { s ->
                        _uiState.update { state ->
                            state.copy(
                                studentName = s.name,
                                studentRate = s.rate,
                                selectedStudentId = id
                            )
                        }
                    }
                }
            } else {
                studentDao.getAllActiveStudents().collect { list ->
                    _uiState.update { it.copy(availableStudents = list) }
                }
            }
        }
    }

    private fun loadLesson() {
        viewModelScope.launch(Dispatchers.IO) {
            lessonId?.takeIf { it != 0L }?.let { id ->
                lessonDao.getLessonById(id).collect { lesson ->
                    lesson?.let { l ->
                        _uiState.update { state ->
                            state.copy(
                                date = LocalDate.parse(l.date).format(dateFormatter),
                                startTime = l.startTime,
                                durationMinutes = l.durationMinutes.toString(),
                                notes = l.notes ?: "",
                                isEditMode = false,
                                isPaid = l.isPaid
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

    fun updateSelectedStudent(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            studentDao.getStudentById(id).collect { student ->
                student?.let { s ->
                    _uiState.update {
                        it.copy(
                            selectedStudentId = id,
                            studentName = s.name,
                            studentRate = s.rate
                        )
                    }
                }
            }
        }
    }

    fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun updatePaid(paid: Boolean) {
        _uiState.update { it.copy(isPaid = paid) }
    }

    private fun isValidDate(value: String): Boolean = try {
        LocalDate.parse(value, dateFormatter)
        true
    } catch (_: Exception) { false }

    private fun isValidTime(value: String): Boolean = try {
        LocalTime.parse(value, timeFormatter)
        true
    } catch (_: Exception) { false }

    fun isFormValid(): Boolean {
        val state = _uiState.value
        val duration = state.durationMinutes.toIntOrNull() ?: 0
        val hasStudent = state.selectedStudentId != null
        val durationOk = duration >= 60
        return hasStudent && durationOk && isValidDate(state.date) && isValidTime(state.startTime)
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun saveLesson() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _uiState.value
            var duration = state.durationMinutes.toIntOrNull() ?: 0
            if (duration <= 0) duration = 60
            if (duration < 60) duration = 60
            _uiState.update { it.copy(durationMinutes = duration.toString()) }
            if (!isFormValid()) return@launch

            val sId = state.selectedStudentId
            sId?.let {
                if (lessonId == null || lessonId == 0L) {
                    val lesson = Lesson(
                        studentId = it,
                        date = LocalDate.parse(state.date, dateFormatter).toString(),
                        startTime = state.startTime,
                        durationMinutes = duration,
                        notes = state.notes.ifBlank { null },
                        isPaid = state.isPaid
                    )
                    lessonDao.insert(lesson)
                } else {
                    lessonId?.let { lId ->
                        val lesson = Lesson(
                            id = lId,
                            studentId = it,
                            date = LocalDate.parse(state.date, dateFormatter).toString(),
                            startTime = state.startTime,
                            durationMinutes = duration,
                            notes = state.notes.ifBlank { null },
                            isPaid = state.isPaid
                        )
                        lessonDao.update(lesson)
                    }
                }
            }

            _uiState.update { it.copy(isEditMode = false) }

            // Navigate back on main thread
            withContext(Dispatchers.Main) {
                onNavigateBack?.invoke()
            }
        }
    }

    fun deleteLesson() {
        viewModelScope.launch(Dispatchers.IO) {
            lessonId?.takeIf { it != 0L }?.let { id ->
                lessonDao.deleteById(id)

                // Navigate back on main thread
                withContext(Dispatchers.Main) {
                    onNavigateBack?.invoke()
                }
            }
        }
    }

    fun calculateFee(): Double {
        val state = _uiState.value
        val duration = state.durationMinutes.toIntOrNull() ?: 0

        return (duration / 60.0) * state.studentRate
    }
}

data class LessonUiState(
    val date: String = "",
    val startTime: String = "",
    val durationMinutes: String = "",
    val notes: String = "",
    val studentName: String = "",
    val studentRate: Double = 0.0,
    val availableStudents: List<Student> = emptyList(),
    val selectedStudentId: Long? = null,
    val isEditMode: Boolean = true,
    val isPaid: Boolean = false
)
