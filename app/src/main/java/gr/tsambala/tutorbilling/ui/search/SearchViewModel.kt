package gr.tsambala.tutorbilling.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val studentDao: StudentDao
) : ViewModel() {
    private val query = MutableStateFlow("")
    val searchQuery: StateFlow<String> = query.asStateFlow()
    private val _results = MutableStateFlow(emptyList<Student>())
    val results: StateFlow<List<Student>> = _results.asStateFlow()

    init {
        collectResults()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun collectResults() {
        viewModelScope.launch {
            query
                .debounce(300)
                .flatMapLatest { studentDao.searchStudentsByName(it) }
                .collect { _results.value = it }
        }
    }

    fun updateQuery(q: String) { query.value = q }
}
