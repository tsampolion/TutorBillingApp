package gr.tsambala.tutorbilling.ui.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.database.LessonWithStudent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val lessonDao: LessonDao,
    private val studentDao: StudentDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LessonsUiState())
    val uiState: StateFlow<LessonsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                lessonDao.getAllLessons(),
                studentDao.getAllActiveStudents()
            ) { lessons, students ->
                val map = students.associateBy { it.id }
                lessons.mapNotNull { lesson ->
                    map[lesson.studentId]?.let { student ->
                        LessonWithStudent(lesson, student)
                    }
                }
            }.collect { list ->
                _uiState.update { it.copy(lessons = list) }
            }
        }
    }
}

data class LessonsUiState(
    val lessons: List<LessonWithStudent> = emptyList()
)
