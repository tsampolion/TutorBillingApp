package gr.tsambala.tutorbilling.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMenuViewModel @Inject constructor(
    private val studentDao: StudentDao,
    private val lessonDao: LessonDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeMenuUiState())
    val uiState: StateFlow<HomeMenuUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                studentDao.getAllActiveStudents(),
                lessonDao.getAllLessons()
            ) { students, lessons ->
                val classCount = students.map { it.className.lowercase() }
                    .filter { it != "unassigned" }
                    .distinct()
                    .size
                HomeMenuUiState(
                    studentCount = students.size,
                    classCount = classCount,
                    lessonCount = lessons.size
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}

data class HomeMenuUiState(
    val studentCount: Int = 0,
    val classCount: Int = 0,
    val lessonCount: Int = 0
)
