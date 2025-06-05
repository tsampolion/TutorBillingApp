package gr.tsambala.tutorbilling.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.StudentDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassesViewModel @Inject constructor(
    private val studentDao: StudentDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassesUiState())
    val uiState: StateFlow<ClassesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            studentDao.getAllActiveStudents().map { students ->
                students.groupBy { it.className }
            }.collect { grouped ->
                _uiState.value = ClassesUiState(grouped)
            }
        }
    }
}

data class ClassesUiState(
    val studentsByClass: Map<String, List<gr.tsambala.tutorbilling.data.model.Student>> = emptyMap()
)
