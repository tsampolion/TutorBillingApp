package gr.tsambala.tutorbilling.ui.student

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.RateType
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.repository.TutorBillingRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * StudentViewModel manages the state for student-related screens.
 *
 * This ViewModel is more complex than HomeViewModel because it handles multiple
 * responsibilities:
 * 1. Displaying student details and their lessons
 * 2. Adding new students
 * 3. Editing existing students
 * 4. Deleting students (with safety checks)
 *
 * It's like having a specialized clerk who handles all paperwork for a
 * specific student - they can show you the records, update them, or create
 * new ones as needed.
 *
 * SavedStateHandle allows us to receive navigation arguments and survive
 * process death (when Android kills the app to free memory).
 */
@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: TutorBillingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Navigation argument for student ID.
     * If null, we're in "add new student" mode.
     * If present, we're viewing/editing an existing student.
     */
    private val studentId: Long? = savedStateHandle.get<Long>("studentId")

    /**
     * Determines if we're in edit mode vs. view mode.
     * Starts as true when adding a new student.
     */
    private var isEditMode = studentId == null

    /**
     * UI state for student screens.
     * This comprehensive state object contains everything needed for:
     * - Student detail view
     * - Add student form
     * - Edit student form
     */
    data class StudentUiState(
        // Student data
        val student: Student? = null,
        val studentName: String = "",
        val rateType: RateType = RateType.HOURLY,
        val hourlyRate: String = "",
        val perLessonRate: String = "",

        // Lessons data
        val lessons: List<LessonDisplay> = emptyList(),
        val totalEarnings: Double = 0.0,
        val totalHours: Double = 0.0,
        val totalLessons: Int = 0,

        // UI state
        val isLoading: Boolean = true,
        val isEditMode: Boolean = false,
        val isSaving: Boolean = false,
        val errorMessage: String? = null,
        val validationErrors: ValidationErrors = ValidationErrors(),

        // Financial summaries
        val currentMonthEarnings: Double = 0.0,
        val lastMonthEarnings: Double = 0.0,

        // Delete confirmation
        val showDeleteConfirmation: Boolean = false,
        val canDelete: Boolean = true
    )

    /**
     * Validation errors for the form fields.
     * Keeping these separate makes it easy to show field-specific errors.
     */
    data class ValidationErrors(
        val nameError: String? = null,
        val rateError: String? = null
    )

    /**
     * Lesson data formatted for display.
     * Includes calculated values to avoid recalculation in the UI.
     */
    data class LessonDisplay(
        val lesson: Lesson,
        val fee: Double,
        val formattedDate: String,
        val formattedTime: String,
        val formattedDuration: String
    )

    private val _uiState = MutableStateFlow(StudentUiState(isEditMode = isEditMode))
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    init {
        if (studentId != null) {
            // Load existing student data
            loadStudent(studentId)
            loadLessons(studentId)
        } else {
            // New student mode - just set loading to false
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    /**
     * Loads student data from the repository.
     * Sets up reactive updates so any changes to the student are reflected immediately.
     */
    private fun loadStudent(studentId: Long) {
        viewModelScope.launch {
            try {
                val student = repository.getStudent(studentId)
                if (student != null) {
                    _uiState.update { state ->
                        state.copy(
                            student = student,
                            studentName = student.name,
                            rateType = student.getRateType(),
                            hourlyRate = student.hourlyRate?.toString() ?: "",
                            perLessonRate = student.perLessonRate?.toString() ?: "",
                            isLoading = false
                        )
                    }

                    // Check if student can be deleted
                    val canDelete = repository.canDeleteStudent(studentId)
                    _uiState.update { it.copy(canDelete = canDelete) }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Student not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load student: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Loads lessons for the student.
     * Calculates various statistics and formats data for display.
     */
    private fun loadLessons(studentId: Long) {
        repository.getLessonsWithStudentData(studentId)
            .catch { e ->
                _uiState.update {
                    it.copy(errorMessage = "Failed to load lessons: ${e.message}")
                }
            }
            .collect { lessonsWithStudent ->
                val lessonDisplays = lessonsWithStudent.map { lessonWithStudent ->
                    LessonDisplay(
                        lesson = lessonWithStudent.lesson,
                        fee = lessonWithStudent.calculateFee(),
                        formattedDate = lessonWithStudent.lesson.date.toString(),
                        formattedTime = lessonWithStudent.lesson.startTime.toString(),
                        formattedDuration = lessonWithStudent.lesson.getFormattedDuration()
                    )
                }

                // Calculate statistics
                val totalEarnings = lessonDisplays.sumOf { it.fee }
                val totalMinutes = lessonsWithStudent.sumOf { it.lesson.durationMinutes }
                val totalHours = totalMinutes / 60.0

                // Calculate monthly earnings
                val currentMonth = LocalDate.now().withDayOfMonth(1)
                val lastMonth = currentMonth.minusMonths(1)

                val currentMonthEarnings = lessonDisplays
                    .filter { it.lesson.date >= currentMonth }
                    .sumOf { it.fee }

                val lastMonthEarnings = lessonDisplays
                    .filter {
                        it.lesson.date >= lastMonth &&
                                it.lesson.date < currentMonth
                    }
                    .sumOf { it.fee }

                _uiState.update {
                    it.copy(
                        lessons = lessonDisplays,
                        totalEarnings = totalEarnings,
                        totalHours = totalHours,
                        totalLessons = lessonDisplays.size,
                        currentMonthEarnings = currentMonthEarnings,
                        lastMonthEarnings = lastMonthEarnings
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Updates the student's name in the form.
     */
    fun updateStudentName(name: String) {
        _uiState.update {
            it.copy(
                studentName = name,
                validationErrors = it.validationErrors.copy(nameError = null)
            )
        }
    }

    /**
     * Updates the rate type (hourly vs. per lesson).
     */
    fun updateRateType(rateType: RateType) {
        _uiState.update {
            it.copy(
                rateType = rateType,
                validationErrors = it.validationErrors.copy(rateError = null)
            )
        }
    }

    /**
     * Updates the hourly rate.
     */
    fun updateHourlyRate(rate: String) {
        // Only allow valid numeric input
        val filtered = rate.filter { it.isDigit() || it == '.' }
        _uiState.update {
            it.copy(
                hourlyRate = filtered,
                validationErrors = it.validationErrors.copy(rateError = null)
            )
        }
    }

    /**
     * Updates the per-lesson rate.
     */
    fun updatePerLessonRate(rate: String) {
        // Only allow valid numeric input
        val filtered = rate.filter { it.isDigit() || it == '.' }
        _uiState.update {
            it.copy(
                perLessonRate = filtered,
                validationErrors = it.validationErrors.copy(rateError = null)
            )
        }
    }

    /**
     * Toggles between view and edit mode.
     */
    fun toggleEditMode() {
        val newEditMode = !_uiState.value.isEditMode
        _uiState.update { it.copy(isEditMode = newEditMode) }

        // If canceling edit, restore original values
        if (!newEditMode && _uiState.value.student != null) {
            val student = _uiState.value.student!!
            _uiState.update {
                it.copy(
                    studentName = student.name,
                    rateType = student.getRateType(),
                    hourlyRate = student.hourlyRate?.toString() ?: "",
                    perLessonRate = student.perLessonRate?.toString() ?: ""
                )
            }
        }
    }

    /**
     * Validates form input and returns true if valid.
     * Updates validation error messages in the UI state.
     */
    private fun validateInput(): Boolean {
        val state = _uiState.value
        val errors = ValidationErrors()
        var isValid = true

        // Validate name
        if (state.studentName.isBlank()) {
            errors.nameError = "Student name is required"
            isValid = false
        }

        // Validate rate based on type
        when (state.rateType) {
            RateType.HOURLY -> {
                val rate = state.hourlyRate.toDoubleOrNull()
                if (rate == null || rate <= 0) {
                    errors.rateError = "Please enter a valid hourly rate"
                    isValid = false
                }
            }
            RateType.PER_LESSON -> {
                val rate = state.perLessonRate.toDoubleOrNull()
                if (rate == null || rate <= 0) {
                    errors.rateError = "Please enter a valid lesson rate"
                    isValid = false
                }
            }
            RateType.NONE -> {
                errors.rateError = "Please select a rate type"
                isValid = false
            }
        }

        _uiState.update { it.copy(validationErrors = errors) }
        return isValid
    }

    /**
     * Saves the student (either creating new or updating existing).
     * Returns true if successful, false otherwise.
     */
    fun saveStudent(onSuccess: () -> Unit) {
        if (!validateInput()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            try {
                val state = _uiState.value
                val student = if (studentId != null && state.student != null) {
                    // Update existing student
                    state.student.copy(
                        name = state.studentName,
                        hourlyRate = if (state.rateType == RateType.HOURLY)
                            state.hourlyRate.toDoubleOrNull() else null,
                        perLessonRate = if (state.rateType == RateType.PER_LESSON)
                            state.perLessonRate.toDoubleOrNull() else null
                    )
                } else {
                    // Create new student
                    Student(
                        name = state.studentName,
                        hourlyRate = if (state.rateType == RateType.HOURLY)
                            state.hourlyRate.toDoubleOrNull() else null,
                        perLessonRate = if (state.rateType == RateType.PER_LESSON)
                            state.perLessonRate.toDoubleOrNull() else null
                    )
                }

                if (studentId != null) {
                    repository.updateStudent(student)
                } else {
                    repository.addStudent(student)
                }

                _uiState.update { it.copy(isSaving = false) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = "Failed to save student: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Shows the delete confirmation dialog.
     */
    fun showDeleteConfirmation() {
        _uiState.update { it.copy(showDeleteConfirmation = true) }
    }

    /**
     * Hides the delete confirmation dialog.
     */
    fun hideDeleteConfirmation() {
        _uiState.update { it.copy(showDeleteConfirmation = false) }
    }

    /**
     * Deletes the student.
     */
    fun deleteStudent(onSuccess: () -> Unit) {
        if (studentId == null) return

        viewModelScope.launch {
            try {
                repository.deleteStudent(studentId)
                onSuccess()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        showDeleteConfirmation = false,
                        errorMessage = "Failed to delete student: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Navigates to add a lesson for this student.
     */
    fun onAddLessonClick(): String? {
        return studentId?.let { "add_lesson/$it" }
    }

    /**
     * Navigates to edit a lesson.
     */
    fun onEditLessonClick(lessonId: Long): String {
        return "edit_lesson/$lessonId"
    }
}