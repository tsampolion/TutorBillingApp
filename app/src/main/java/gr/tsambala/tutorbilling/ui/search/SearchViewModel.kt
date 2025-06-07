package gr.tsambala.tutorbilling.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.StudentDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val studentDao: StudentDao
) : ViewModel() {
    private val query = MutableStateFlow("")
    val searchQuery: StateFlow<String> = query.asStateFlow()
    private val _results = MutableStateFlow(emptyList<gr.tsambala.tutorbilling.data.model.Student>())
    val results: StateFlow<List<gr.tsambala.tutorbilling.data.model.Student>> = _results.asStateFlow()

    init {
        viewModelScope.launch {
            query
                .debounce(300)
                .flatMapLatest { studentDao.searchStudentsByName(it) }
                .collect { _results.value = it }
        }
    }

    fun updateQuery(q: String) { query.value = q }
}
