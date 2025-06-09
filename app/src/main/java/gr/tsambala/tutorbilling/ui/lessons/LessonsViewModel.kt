package gr.tsambala.tutorbilling.ui.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.database.LessonWithStudent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val lessonDao: LessonDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LessonsUiState())
    val uiState: StateFlow<LessonsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            lessonDao.getLessonsWithStudents().collect { list ->
                _uiState.update { it.copy(lessons = list) }
            }
        }
    }

    fun updatePaid(lessonId: Long, paid: Boolean) {
        viewModelScope.launch {
            lessonDao.updatePaidStatus(listOf(lessonId), paid)
        }
    }
}

data class LessonsUiState(
    val lessons: List<LessonWithStudent> = emptyList()
)
