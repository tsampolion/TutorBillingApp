// StudentViewModel.kt - Fixed navigation thread issue
package gr.tsambala.tutorbilling.ui.student

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.model.RateTypes
import gr.tsambala.tutorbilling.data.model.calculateFee
import gr.tsambala.tutorbilling.data.repository.StudentRepository
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.utils.EarningsCalculator
import gr.tsambala.tutorbilling.utils.ClassOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Patterns
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val lessonDao: LessonDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val studentId: Long = savedStateHandle.get<Long>("studentId") ?: 0L

    private val classOptions = ClassOptions.DEFAULT

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
                        val existingClass = student?.className ?: ""
                        val (selectedClass, customClass) = if (existingClass.isNotBlank() && existingClass !in classOptions) {
                            "Custom" to existingClass
                        } else {
                            existingClass to ""
                        }
                        currentState.copy(
                            student = student,
                            name = if (currentState.isEditMode) currentState.name else student?.name ?: "",
                            surname = if (currentState.isEditMode) currentState.surname else student?.surname ?: "",
                            parentMobile = if (currentState.isEditMode) currentState.parentMobile else student?.parentMobile ?: "",
                            parentEmail = if (currentState.isEditMode) currentState.parentEmail else student?.parentEmail ?: "",
                            selectedClass = if (currentState.isEditMode) currentState.selectedClass else selectedClass,
                            customClass = if (currentState.isEditMode) currentState.customClass else customClass,
                            rate = if (currentState.isEditMode) currentState.rate else student?.rate?.toString() ?: "",
                            rateType = if (currentState.isEditMode) currentState.rateType else student?.rateType ?: RateTypes.HOURLY,
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

    fun updateSurname(value: String) {
        _uiState.update { it.copy(surname = value, hasChanges = true) }
    }

    fun updateParentMobile(value: String) {
        val digitsOnly = value.filter { it.isDigit() }.take(10)
        _uiState.update { it.copy(parentMobile = digitsOnly, hasChanges = true) }
    }

    fun updateParentEmail(value: String) {
        _uiState.update { it.copy(parentEmail = value, hasChanges = true) }
    }

    fun updateSelectedClass(value: String) {
        _uiState.update {
            val custom = if (value == "Custom") it.customClass else ""
            it.copy(selectedClass = value, customClass = custom, hasChanges = true)
        }
    }

    fun updateCustomClass(value: String) {
        _uiState.update { it.copy(customClass = value, hasChanges = true) }
    }

    fun updateRate(rate: String) {
        val sanitized = buildString {
            var dotSeen = false
            for (ch in rate) {
                when {
                    ch.isDigit() -> append(ch)
                    ch == '.' && !dotSeen -> {
                        append(ch)
                        dotSeen = true
                    }
                }
            }
        }
        _uiState.update { it.copy(rate = sanitized, hasChanges = true) }
    }

    fun updateRateType(type: String) {
        _uiState.update { it.copy(rateType = type, hasChanges = true) }
    }

    fun toggleActive() {
        _uiState.update { it.copy(isActive = !it.isActive, hasChanges = true) }
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun saveStudent() {
        val state = _uiState.value
        val rate = state.rate.toDoubleOrNull()?.takeIf { it > 0 } ?: return

        if (state.parentMobile.length != 10) return

        if (state.parentEmail.isNotBlank() &&
            !Patterns.EMAIL_ADDRESS.matcher(state.parentEmail).matches()) {
            return
        }
        val className = if (state.selectedClass == "Custom") state.customClass.trim() else state.selectedClass

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                if (state.selectedClass == "Custom" && studentRepository.classNameExists(className)) {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Class already exists")
                    }
                    return@launch
                }
                val student = if (studentId > 0) {
                    Student(
                        id = studentId,
                        name = state.name,
                        surname = state.surname,
                        parentMobile = state.parentMobile,
                        parentEmail = state.parentEmail.ifBlank { null },
                        className = className,
                        rate = rate,
                        rateType = state.rateType,
                        isActive = state.isActive
                    )
                } else {
                    Student(
                        name = state.name,
                        surname = state.surname,
                        parentMobile = state.parentMobile,
                        parentEmail = state.parentEmail.ifBlank { null },
                        className = className,
                        rate = rate,
                        rateType = state.rateType,
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
    val surname: String = "",
    val parentMobile: String = "",
    val parentEmail: String = "",
    val selectedClass: String = "",
    val customClass: String = "",
    val rate: String = "",
    val rateType: String = RateTypes.HOURLY,
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
