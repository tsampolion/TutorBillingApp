// StudentViewModel.kt - Fixed navigation thread issue
package gr.tsambala.tutorbilling.ui.student

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val studentId: Long = savedStateHandle.get<Long>("studentId") ?: 0L

    // UI State
    private val _uiState = MutableStateFlow(StudentUiState(isEditMode = studentId == 0L))
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    // Navigation callback
    private var onNavigateBack: (() -> Unit)? = null

    init {
        if (studentId > 0) {
            loadStudent()
        }
    }

    fun setNavigationCallback(callback: () -> Unit) {
        onNavigateBack = callback
    }

    private fun loadStudent() {
        viewModelScope.launch {
            studentRepository.getStudentById(studentId)
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
                .collect { student ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            student = student,
                            name = student?.name ?: "",
                            rate = student?.rate?.toString() ?: "",
                            isActive = student?.isActive ?: true
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
    val errorMessage: String? = null
)