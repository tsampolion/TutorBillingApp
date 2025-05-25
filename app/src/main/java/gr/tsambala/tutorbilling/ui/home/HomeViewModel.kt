package gr.tsambala.tutorbilling.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.repository.TutorBillingRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HomeViewModel manages the state and logic for the home screen.
 *
 * Think of ViewModels as the managers of your app's screens. They:
 * 1. Survive configuration changes (like screen rotation)
 * 2. Provide data to the UI in a lifecycle-aware way
 * 3. Handle user interactions and business logic
 * 4. Keep the UI layer simple and focused on display
 *
 * This ViewModel is like a restaurant manager who coordinates between
 * the kitchen (Repository) and the dining room (UI), making sure
 * customers (users) get what they need efficiently.
 *
 * @HiltViewModel tells Hilt this is a ViewModel that needs dependency injection
 * @Inject constructor tells Hilt to provide the repository automatically
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TutorBillingRepository
) : ViewModel() {

    /**
     * UI state for the home screen.
     * This data class holds all the information the home screen needs to display.
     *
     * Using a single state object makes it easy to manage and test the UI state.
     * It's like having a dashboard that shows all important information at once.
     */
    data class HomeUiState(
        val studentSummaries: List<StudentSummary> = emptyList(),
        val isLoading: Boolean = true,
        val totalWeekEarnings: Double = 0.0,
        val totalMonthEarnings: Double = 0.0,
        val searchQuery: String = "",
        val errorMessage: String? = null
    )

    /**
     * Simplified student summary for display on the home screen.
     * Contains just the information needed for the list items.
     */
    data class StudentSummary(
        val id: Long,
        val name: String,
        val weekTotal: Double,
        val monthTotal: Double,
        val formattedRate: String,
        val lessonCount: Int
    )

    /**
     * Private mutable state flow that we can update within the ViewModel.
     * Think of this as the internal dashboard controls that only the manager can adjust.
     */
    private val _uiState = MutableStateFlow(HomeUiState())

    /**
     * Public state flow that the UI observes.
     * This is the read-only dashboard that the UI can watch but not modify directly.
     * Using StateFlow ensures the UI always has the latest state.
     */
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Search query as a separate flow for debouncing.
     * This prevents too many database queries while the user is typing.
     */
    private val searchQuery = MutableStateFlow("")

    init {
        // Start loading data as soon as the ViewModel is created
        loadStudentSummaries()

        // Set up search with debouncing
        // This waits 300ms after the user stops typing before searching
        // It's like a waiter who waits a moment to make sure you're done
        // ordering before rushing to the kitchen
        searchQuery
            .debounce(300) // Wait 300ms after user stops typing
            .distinctUntilChanged() // Only search if query actually changed
            .onEach { query ->
                _uiState.update { it.copy(searchQuery = query) }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Loads student summaries from the repository.
     * This sets up a reactive data flow - whenever the underlying data changes,
     * the UI automatically updates. It's like having a live dashboard that
     * refreshes itself whenever new information arrives.
     */
    private fun loadStudentSummaries() {
        repository.getStudentFinancialSummaries()
            .onStart {
                // Show loading state when we start fetching data
                _uiState.update { it.copy(isLoading = true) }
            }
            .map { summaries ->
                // Transform repository data into UI-friendly format
                summaries.map { summary ->
                    StudentSummary(
                        id = summary.student.id,
                        name = summary.student.name,
                        weekTotal = summary.weekTotal,
                        monthTotal = summary.monthTotal,
                        formattedRate = summary.student.getFormattedRate(),
                        lessonCount = summary.lessonCount
                    )
                }
            }
            .combine(searchQuery) { summaries, query ->
                // Filter summaries based on search query
                if (query.isBlank()) {
                    summaries
                } else {
                    summaries.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                }
            }
            .catch { exception ->
                // Handle any errors gracefully
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load students: ${exception.message}"
                    )
                }
            }
            .collect { filteredSummaries ->
                // Calculate totals and update UI state
                val weekTotal = filteredSummaries.sumOf { it.weekTotal }
                val monthTotal = filteredSummaries.sumOf { it.monthTotal }

                _uiState.update {
                    it.copy(
                        studentSummaries = filteredSummaries,
                        isLoading = false,
                        totalWeekEarnings = weekTotal,
                        totalMonthEarnings = monthTotal,
                        errorMessage = null
                    )
                }
            }
            .launchIn(viewModelScope) // Run in ViewModel's lifecycle scope
    }

    /**
     * Updates the search query.
     * Called when user types in the search box.
     */
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    /**
     * Clears the search query.
     * Called when user taps the clear button in search.
     */
    fun clearSearch() {
        searchQuery.value = ""
    }

    /**
     * Navigates to add a new student.
     * Returns the navigation destination for the UI to handle.
     *
     * Note: In a real app, you might use a navigation event channel
     * to prevent issues with configuration changes.
     */
    fun onAddStudentClick(): String {
        return "add_student"
    }

    /**
     * Navigates to student details.
     * Returns the navigation destination with student ID.
     */
    fun onStudentClick(studentId: Long): String {
        return "student_detail/$studentId"
    }

    /**
     * Refreshes the data.
     * Called when user pulls to refresh.
     *
     * Even though our data is reactive and updates automatically,
     * users expect pull-to-refresh in modern apps.
     */
    fun refresh() {
        // The data flow is already reactive, but we can force a refresh
        // by resubscribing to the flow
        loadStudentSummaries()
    }

    /**
     * Clears any error messages.
     * Called when user dismisses an error snackbar.
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * Quick action to add a lesson for a student.
     * This is a convenience method for the home screen.
     */
    fun onQuickAddLessonClick(studentId: Long): String {
        return "add_lesson/$studentId"
    }
}