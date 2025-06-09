package gr.tsambala.tutorbilling.ui.student

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.model.RateTypes
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.dao.LessonDao
import android.util.Patterns
import gr.tsambala.tutorbilling.utils.titleCase
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
        viewModelScope.launch(Dispatchers.IO) {
            studentId?.toLongOrNull()?.let { id ->
                studentDao.getStudentById(id).collect { student ->
                    student?.let { s ->
                        _uiState.update { state ->
                            state.copy(
                                name = s.name,
                                surname = s.surname,
                                parentMobile = s.parentMobile,
                                parentEmail = s.parentEmail,
                                rateType = s.rateType,
                                rate = s.rate.toString(),
                                className = s.className,
                                isEditMode = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadLessons() {
        viewModelScope.launch(Dispatchers.IO) {
            studentId?.toLongOrNull()?.let { id ->
                lessonDao.getLessonsByStudentId(id).collect { lessons ->
                    val today = LocalDate.now()
                    val weekFields = WeekFields.of(Locale.getDefault())
                    val currentWeek = today.get(weekFields.weekOfWeekBasedYear())
                    val currentMonth = today.monthValue
                    val currentYear = today.year

                    val rate = _uiState.value.rate.toDoubleOrNull() ?: 0.0
                    val rateType = _uiState.value.rateType
                    fun calcFee(mins: Int): Double =
                        if (rateType == RateTypes.HOURLY) (mins / 60.0) * rate else rate

                    val weekEarnings = lessons
                        .filter { lesson ->
                            val lessonDate = LocalDate.parse(lesson.date)
                            lessonDate.year == currentYear &&
                                    lessonDate.get(weekFields.weekOfWeekBasedYear()) == currentWeek
                        }
                        .sumOf { calcFee(it.durationMinutes) }

                    val monthEarnings = lessons
                        .filter { lesson ->
                            val lessonDate = LocalDate.parse(lesson.date)
                            lessonDate.year == currentYear &&
                                    lessonDate.monthValue == currentMonth
                        }
                        .sumOf { calcFee(it.durationMinutes) }

                    val totalEarnings = lessons.sumOf { calcFee(it.durationMinutes) }

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
        _uiState.update { it.copy(name = name.titleCase()) }
    }

    fun updateSurname(surname: String) {
        _uiState.update { it.copy(surname = surname.titleCase()) }
    }

    fun updateParentMobile(mobile: String) {
        _uiState.update { it.copy(parentMobile = mobile) }
    }

    fun updateParentEmail(email: String) {
        _uiState.update { it.copy(parentEmail = email) }
    }

    fun updateRateType(rateType: String) {
        _uiState.update { it.copy(rateType = rateType) }
    }

    fun updateRate(rate: String) {
        _uiState.update { it.copy(rate = rate) }
    }

    fun updateClassName(className: String) {
        _uiState.update { it.copy(className = className) }
    }

    fun updateCustomClass(name: String) {
        _uiState.update { it.copy(customClass = name, classError = false) }
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun saveStudent() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _uiState.value
            val rate = state.rate.toDoubleOrNull() ?: 0.0
            val finalClass = if (state.className == "Custom") state.customClass else state.className

            val existingClasses = studentDao.getAllActiveStudents().first().map { it.className.lowercase() }
            if (state.className == "Custom" && existingClasses.contains(finalClass.lowercase())) {
                _uiState.update { it.copy(classError = true) }
                return@launch
            }

            if (studentId == "new") {
                val student = Student(
                    name = state.name.titleCase(),
                    surname = state.surname.titleCase(),
                    parentMobile = state.parentMobile,
                    parentEmail = state.parentEmail,
                    rateType = state.rateType,
                    rate = rate,
                    className = finalClass
                )
                studentDao.insert(student)
            } else {
                studentId?.toLongOrNull()?.let { id ->
                    val student = Student(
                        id = id,
                        name = state.name.titleCase(),
                        surname = state.surname.titleCase(),
                        parentMobile = state.parentMobile,
                        parentEmail = state.parentEmail,
                        rateType = state.rateType,
                        rate = rate,
                        className = finalClass
                    )
                    studentDao.update(student)
                }
            }

            _uiState.update { it.copy(isEditMode = false) }
        }
    }

    fun deleteStudent(onDeleted: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            studentId?.toLongOrNull()?.let { id ->
                studentDao.softDeleteStudent(id)
                onDeleted()
            }
        }
    }

    fun deleteLesson(lessonId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            lessonDao.deleteById(lessonId)
        }
    }

    fun isPhoneValid(phone: String): Boolean = phone.matches(Regex("^\\d{10}$"))

    fun isEmailValid(email: String): Boolean =
        email.isBlank() || Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isFormValid(): Boolean {
        val state = _uiState.value
        val hasClass = if (state.className == "Custom") state.customClass.isNotBlank() else state.className != "Unassigned"
        return state.name.isNotBlank() &&
                state.surname.isNotBlank() &&
                isPhoneValid(state.parentMobile) &&
                isEmailValid(state.parentEmail) &&
                hasClass &&
                state.rate.toDoubleOrNull() != null &&
                !state.classError
    }
}

data class StudentUiState(
    val name: String = "",
    val surname: String = "",
    val parentMobile: String = "",
    val parentEmail: String = "",
    val rateType: String = RateTypes.HOURLY,
    val rate: String = "",
    val className: String = "Unassigned",
    val customClass: String = "",
    val classError: Boolean = false,
    val lessons: List<Lesson> = emptyList(),
    val weekEarnings: Double = 0.0,
    val monthEarnings: Double = 0.0,
    val totalEarnings: Double = 0.0,
    val isEditMode: Boolean = true
)