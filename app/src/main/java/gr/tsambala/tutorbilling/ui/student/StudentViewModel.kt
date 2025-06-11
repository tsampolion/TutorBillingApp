// StudentViewModel.kt - Fixed navigation thread issue
package gr.tsambala.tutorbilling.ui.student

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.model.calculateFee
import gr.tsambala.tutorbilling.data.repository.StudentRepository
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.utils.EarningsCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val lessonDao: LessonDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val studentId: Long = savedStateHandle.get<Long>("studentId") ?: 0L

    // UI State
    private val _uiState = MutableStateFlow(StudentUiState(isEditMode = studentId == 0L))
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    // Navigation callback
    private var onNavigateBack: (() -> Unit)? = null

    init {
        if (studentId > 0) {
            loadData()
        }
    }

    fun setNavigationCallback(callback: () -> Unit) {
        onNavigateBack = callback
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                studentRepository.getStudentById(studentId),
                lessonDao.getLessonsByStudentId(studentId)
            ) { student, lessons -> student to lessons }
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
                .collect { (student, lessons) ->
                    val (week, month) = student?.let { EarningsCalculator.calculate(it, lessons) } ?: (0.0 to 0.0)
                    val total = student?.let { lessons.sumOf { l -> l.calculateFee(it) } } ?: 0.0
                    _uiState.update { currentState ->
                        currentState.copy(
                            student = student,
                            name = if (currentState.isEditMode) currentState.name else student?.name ?: "",
                            rate = if (currentState.isEditMode) currentState.rate else student?.rate?.toString() ?: "",
                            isActive = if (currentState.isEditMode) currentState.isActive else student?.isActive ?: true,
                            lessons = lessons,
                            weekEarnings = week,
                            monthEarnings = month,
                            totalEarnings = total
                        )
                    }
                }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name, hasChanges = true) }
    }

    fun updateRate(rate: String) {
        _uiState.update { it.copy(rate = rate, hasChanges = true) }
    }

    fun toggleActive() {
        _uiState.update { it.copy(isActive = !it.isActive, hasChanges = true) }
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun saveStudent() {
        val state = _uiState.value
        val rate = state.rate.toDoubleOrNull() ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val student = if (studentId > 0) {
                    Student(
                        id = studentId,
                        name = state.name,
                        rate = rate,
                        isActive = state.isActive
                    )
                } else {
                    Student(
                        name = state.name,
                        rate = rate,
                        isActive = state.isActive
                    )
                }

                if (studentId > 0) {
                    studentRepository.updateStudent(student)
                } else {
                    studentRepository.insertStudent(student)
                }

                // Navigate back on main thread
                withContext(Dispatchers.Main) {
                    onNavigateBack?.invoke()
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to save student: ${e.message}"
                    )
                }
            }
        }
    }

    fun deleteStudent() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                _uiState.value.student?.let { student ->
                    studentRepository.deleteStudent(student)

                    // Navigate back on main thread
                    withContext(Dispatchers.Main) {
                        onNavigateBack?.invoke()
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to delete student: ${e.message}"
                    )
                }
            }
        }
    }

    fun deleteLesson(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            lessonDao.deleteById(id)
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class StudentUiState(
    val student: Student? = null,
    val name: String = "",
    val rate: String = "",
    val isActive: Boolean = true,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val hasChanges: Boolean = false,
    val errorMessage: String? = null,
    val lessons: List<Lesson> = emptyList(),
    val weekEarnings: Double = 0.0,
    val monthEarnings: Double = 0.0,
    val totalEarnings: Double = 0.0
)
