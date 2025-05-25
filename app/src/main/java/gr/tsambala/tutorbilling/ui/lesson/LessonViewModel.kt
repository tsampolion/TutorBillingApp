package gr.tsambala.tutorbilling.ui.lesson

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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

/**
 * LessonViewModel manages the state for lesson-related screens.
 *
 * This ViewModel handles the complex task of coordinating lesson data entry.
 * It's like having a scheduling assistant who not only books appointments
 * but also calculates fees, validates time slots, and ensures everything
 * is properly recorded.
 *
 * The ViewModel handles both creating new lessons and editing existing ones,
 * adapting its behavior based on the navigation parameters it receives.
 */
@HiltViewModel
class LessonViewModel @Inject constructor(
    private val repository: TutorBillingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Navigation parameters extracted from SavedStateHandle.
     * These determine the mode and context of the screen.
     */
    private val lessonId: Long? = savedStateHandle.get<Long>("lessonId")
    private val preselectedStudentId: Long? = savedStateHandle.get<Long>("studentId")

    /**
     * Determines if we're editing an existing lesson or creating a new one.
     */
    private val isEditMode = lessonId != null

    /**
     * UI state for lesson screens.
     * This comprehensive state covers all aspects of lesson entry and editing.
     */
    data class LessonUiState(
        // Student selection
        val students: List<Student> = emptyList(),
        val selectedStudent: Student? = null,
        val selectedStudentId: Long? = null,

        // Lesson data
        val date: LocalDate = LocalDate.now(),
        val startTime: LocalTime = LocalTime.now().withSecond(0).withNano(0),
        val durationMinutes: String = "60", // Default to 1 hour
        val notes: String = "",

        // Calculated values
        val calculatedFee: Double = 0.0,
        val endTime: LocalTime = LocalTime.now().plusHours(1),

        // UI state
        val isLoading: Boolean = true,
        val isSaving: Boolean = false,
        val isEditMode: Boolean = false,
        val errorMessage: String? = null,
        val validationErrors: ValidationErrors = ValidationErrors(),

        // Date/Time picker states
        val showDatePicker: Boolean = false,
        val showTimePicker: Boolean = false,

        // Quick duration options for easy selection
        val quickDurationOptions: List<Int> = listOf(30, 45, 60, 90, 120)
    )

    /**
     * Validation errors for form fields.
     * Specific error messages help users correct their input.
     */
    data class ValidationErrors(
        val studentError: String? = null,
        val dateError: String? = null,
        val timeError: String? = null,
        val durationError: String? = null
    )

    private val _uiState = MutableStateFlow(LessonUiState(isEditMode = isEditMode))
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    init {
        // Load available students for selection
        loadStudents()

        // If editing, load the existing lesson
        if (lessonId != null) {
            loadLesson(lessonId)
        } else if (preselectedStudentId != null) {
            // If adding a lesson from student detail screen, preselect that student
            preselectStudent(preselectedStudentId)
        }
    }

    /**
     * Loads all active students for the selection dropdown.
     * Students are the foundation of lesson creation - you can't have
     * a lesson without a student to teach!
     */
    private fun loadStudents() {
        repository.getAllActiveStudents()
            .catch { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load students: ${e.message}"
                    )
                }
            }
            .collect { students ->
                _uiState.update { state ->
                    state.copy(
                        students = students,
                        isLoading = if (isEditMode) state.isLoading else false
                    )
                }

                // If we have a preselected student ID, select it
                if (preselectedStudentId != null && _uiState.value.selectedStudent == null) {
                    students.find { it.id == preselectedStudentId }?.let { student ->
                        selectStudent(student)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Loads an existing lesson for editing.
     * Populates all form fields with the lesson's current values.
     */
    private fun loadLesson(lessonId: Long) {
        viewModelScope.launch {
            try {
                val lesson = repository.getLessonById(lessonId)
                if (lesson != null) {
                    // Load the student for this lesson
                    val student = repository.getStudent(lesson.studentId)

                    _uiState.update { state ->
                        state.copy(
                            selectedStudent = student,
                            selectedStudentId = student?.id,
                            date = lesson.date,
                            startTime = lesson.startTime,
                            durationMinutes = lesson.durationMinutes.toString(),
                            notes = lesson.notes ?: "",
                            calculatedFee = student?.let { lesson.calculateFee(it) } ?: 0.0,
                            endTime = lesson.getEndTime(),
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Lesson not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load lesson: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Preselects a student when adding a lesson from their detail page.
     * This saves the user a step and provides better UX flow.
     */
    private fun preselectStudent(studentId: Long) {
        viewModelScope.launch {
            try {
                val student = repository.getStudent(studentId)
                student?.let { selectStudent(it) }
            } catch (e: Exception) {
                // If we can't load the student, just continue without preselection
            }
        }
    }

    /**
     * Selects a student and recalculates the fee.
     * The fee calculation is immediate so users can see the cost as they plan.
     */
    fun selectStudent(student: Student) {
        _uiState.update { state ->
            state.copy(
                selectedStudent = student,
                selectedStudentId = student.id,
                calculatedFee = calculateFee(student, state.durationMinutes.toIntOrNull() ?: 0),
                validationErrors = state.validationErrors.copy(studentError = null)
            )
        }
    }

    /**
     * Updates the lesson date.
     * Future enhancement: could validate against student's availability.
     */
    fun updateDate(date: LocalDate) {
        _uiState.update {
            it.copy(
                date = date,
                showDatePicker = false,
                validationErrors = it.validationErrors.copy(dateError = null)
            )
        }
    }

    /**
     * Updates the start time and recalculates the end time.
     * Times are rounded to the nearest minute for simplicity.
     */
    fun updateStartTime(time: LocalTime) {
        val roundedTime = time.withSecond(0).withNano(0)
        _uiState.update { state ->
            val duration = state.durationMinutes.toIntOrNull() ?: 0
            state.copy(
                startTime = roundedTime,
                endTime = roundedTime.plusMinutes(duration.toLong()),
                showTimePicker = false,
                validationErrors = state.validationErrors.copy(timeError = null)
            )
        }
    }

    /**
     * Updates the duration and recalculates both end time and fee.
     * This is where the magic happens - as users adjust duration,
     * they immediately see how it affects the lesson end time and cost.
     */
    fun updateDuration(durationStr: String) {
        // Only allow numeric input
        val filtered = durationStr.filter { it.isDigit() }
        val duration = filtered.toIntOrNull() ?: 0

        _uiState.update { state ->
            state.copy(
                durationMinutes = filtered,
                endTime = state.startTime.plusMinutes(duration.toLong()),
                calculatedFee = state.selectedStudent?.let {
                    calculateFee(it, duration)
                } ?: 0.0,
                validationErrors = state.validationErrors.copy(durationError = null)
            )
        }
    }

    /**
     * Quick duration setter for common lesson lengths.
     * These buttons make it easy to set standard lesson durations.
     */
    fun setQuickDuration(minutes: Int) {
        updateDuration(minutes.toString())
    }

    /**
     * Updates the optional notes field.
     * Teachers can record topics covered, homework assigned, etc.
     */
    fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    /**
     * Shows or hides the date picker dialog.
     */
    fun toggleDatePicker() {
        _uiState.update { it.copy(showDatePicker = !it.showDatePicker) }
    }

    /**
     * Shows or hides the time picker dialog.
     */
    fun toggleTimePicker() {
        _uiState.update { it.copy(showTimePicker = !it.showTimePicker) }
    }

    /**
     * Calculates the fee based on student rate and duration.
     * This is the core business logic that makes the app valuable.
     */
    private fun calculateFee(student: Student, durationMinutes: Int): Double {
        return when (student.getRateType()) {
            RateType.HOURLY -> {
                val hours = durationMinutes / 60.0
                (student.hourlyRate ?: 0.0) * hours
            }
            RateType.PER_LESSON -> {
                student.perLessonRate ?: 0.0
            }
            RateType.NONE -> 0.0
        }
    }

    /**
     * Validates all form inputs before saving.
     * Good validation prevents data errors and provides clear user feedback.
     */
    private fun validateInput(): Boolean {
        val state = _uiState.value
        val errors = ValidationErrors()
        var isValid = true

        // Validate student selection
        if (state.selectedStudent == null) {
            errors.studentError = "Please select a student"
            isValid = false
        }

        // Validate date (could check for future dates, weekends, etc.)
        if (state.date.isAfter(LocalDate.now().plusDays(1))) {
            errors.dateError = "Cannot schedule lessons more than one day in advance"
            isValid = false
        }

        // Validate duration
        val duration = state.durationMinutes.toIntOrNull()
        if (duration == null || duration <= 0) {
            errors.durationError = "Please enter a valid duration"
            isValid = false
        } else if (duration > 480) { // 8 hours
            errors.durationError = "Duration cannot exceed 8 hours"
            isValid = false
        }

        _uiState.update { it.copy(validationErrors = errors) }
        return isValid
    }

    /**
     * Saves the lesson (create new or update existing).
     * This is the culmination of all the data entry - actually persisting the lesson.
     */
    fun saveLesson(onSuccess: () -> Unit) {
        if (!validateInput()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            try {
                val state = _uiState.value
                val lesson = if (lessonId != null) {
                    // Update existing lesson
                    Lesson(
                        id = lessonId,
                        studentId = state.selectedStudentId!!,
                        date = state.date,
                        startTime = state.startTime,
                        durationMinutes = state.durationMinutes.toInt(),
                        notes = state.notes.ifBlank { null }
                    )
                } else {
                    // Create new lesson
                    Lesson(
                        studentId = state.selectedStudentId!!,
                        date = state.date,
                        startTime = state.startTime,
                        durationMinutes = state.durationMinutes.toInt(),
                        notes = state.notes.ifBlank { null }
                    )
                }

                if (lessonId != null) {
                    repository.updateLesson(lesson)
                } else {
                    repository.addLesson(lesson)
                }

                _uiState.update { it.copy(isSaving = false) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = "Failed to save lesson: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Formats the date for display in the UI.
     * Using localized formatting for international users.
     */
    fun getFormattedDate(): String {
        return _uiState.value.date.format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        )
    }

    /**
     * Formats the time range for display.
     * Shows both start and calculated end time.
     */
    fun getFormattedTimeRange(): String {
        val state = _uiState.value
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return "${state.startTime.format(timeFormatter)} - ${state.endTime.format(timeFormatter)}"
    }
}