package gr.tsambala.tutorbilling.ui.home;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.tsambala.tutorbilling.data.repository.TutorBillingRepository;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001:\u0002\u001a\u001bB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\b\u0010\u0011\u001a\u00020\u000fH\u0002J\u0006\u0010\u0012\u001a\u00020\tJ\u000e\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u000fJ\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\tR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001c"}, d2 = {"Lgr/tsambala/tutorbilling/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository;", "(Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lgr/tsambala/tutorbilling/ui/home/HomeViewModel$HomeUiState;", "searchQuery", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "clearSearch", "loadStudentSummaries", "onAddStudentClick", "onQuickAddLessonClick", "studentId", "", "onStudentClick", "refresh", "updateSearchQuery", "query", "HomeUiState", "StudentSummary", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.repository.TutorBillingRepository repository = null;
    
    /**
     * Private mutable state flow that we can update within the ViewModel.
     * Think of this as the internal dashboard controls that only the manager can adjust.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<gr.tsambala.tutorbilling.ui.home.HomeViewModel.HomeUiState> _uiState = null;
    
    /**
     * Public state flow that the UI observes.
     * This is the read-only dashboard that the UI can watch but not modify directly.
     * Using StateFlow ensures the UI always has the latest state.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.home.HomeViewModel.HomeUiState> uiState = null;
    
    /**
     * Search query as a separate flow for debouncing.
     * This prevents too many database queries while the user is typing.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> searchQuery = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.repository.TutorBillingRepository repository) {
        super();
    }
    
    /**
     * Public state flow that the UI observes.
     * This is the read-only dashboard that the UI can watch but not modify directly.
     * Using StateFlow ensures the UI always has the latest state.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.home.HomeViewModel.HomeUiState> getUiState() {
        return null;
    }
    
    /**
     * Loads student summaries from the repository.
     * This sets up a reactive data flow - whenever the underlying data changes,
     * the UI automatically updates. It's like having a live dashboard that
     * refreshes itself whenever new information arrives.
     */
    private final void loadStudentSummaries() {
    }
    
    /**
     * Updates the search query.
     * Called when user types in the search box.
     */
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    /**
     * Clears the search query.
     * Called when user taps the clear button in search.
     */
    public final void clearSearch() {
    }
    
    /**
     * Navigates to add a new student.
     * Returns the navigation destination for the UI to handle.
     *
     * Note: In a real app, you might use a navigation event channel
     * to prevent issues with configuration changes.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onAddStudentClick() {
        return null;
    }
    
    /**
     * Navigates to student details.
     * Returns the navigation destination with student ID.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onStudentClick(long studentId) {
        return null;
    }
    
    /**
     * Refreshes the data.
     * Called when user pulls to refresh.
     *
     * Even though our data is reactive and updates automatically,
     * users expect pull-to-refresh in modern apps.
     */
    public final void refresh() {
    }
    
    /**
     * Clears any error messages.
     * Called when user dismisses an error snackbar.
     */
    public final void clearError() {
    }
    
    /**
     * Quick action to add a lesson for a student.
     * This is a convenience method for the home screen.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onQuickAddLessonClick(long studentId) {
        return null;
    }
    
    /**
     * UI state for the home screen.
     * This data class holds all the information the home screen needs to display.
     *
     * Using a single state object makes it easy to manage and test the UI state.
     * It's like having a dashboard that shows all important information at once.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\rJ\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JM\u0010\u001d\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u00062\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u000bH\u00d6\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015\u00a8\u0006#"}, d2 = {"Lgr/tsambala/tutorbilling/ui/home/HomeViewModel$HomeUiState;", "", "studentSummaries", "", "Lgr/tsambala/tutorbilling/ui/home/HomeViewModel$StudentSummary;", "isLoading", "", "totalWeekEarnings", "", "totalMonthEarnings", "searchQuery", "", "errorMessage", "(Ljava/util/List;ZDDLjava/lang/String;Ljava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "()Z", "getSearchQuery", "getStudentSummaries", "()Ljava/util/List;", "getTotalMonthEarnings", "()D", "getTotalWeekEarnings", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<gr.tsambala.tutorbilling.ui.home.HomeViewModel.StudentSummary> studentSummaries = null;
        private final boolean isLoading = false;
        private final double totalWeekEarnings = 0.0;
        private final double totalMonthEarnings = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String errorMessage = null;
        
        public HomeUiState(@org.jetbrains.annotations.NotNull()
        java.util.List<gr.tsambala.tutorbilling.ui.home.HomeViewModel.StudentSummary> studentSummaries, boolean isLoading, double totalWeekEarnings, double totalMonthEarnings, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<gr.tsambala.tutorbilling.ui.home.HomeViewModel.StudentSummary> getStudentSummaries() {
            return null;
        }
        
        public final boolean isLoading() {
            return false;
        }
        
        public final double getTotalWeekEarnings() {
            return 0.0;
        }
        
        public final double getTotalMonthEarnings() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSearchQuery() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getErrorMessage() {
            return null;
        }
        
        public HomeUiState() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<gr.tsambala.tutorbilling.ui.home.HomeViewModel.StudentSummary> component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        public final double component4() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.home.HomeViewModel.HomeUiState copy(@org.jetbrains.annotations.NotNull()
        java.util.List<gr.tsambala.tutorbilling.ui.home.HomeViewModel.StudentSummary> studentSummaries, boolean isLoading, double totalWeekEarnings, double totalMonthEarnings, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    /**
     * Simplified student summary for display on the home screen.
     * Contains just the information needed for the list items.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u000bH\u00c6\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\u000bH\u00d6\u0001J\t\u0010\"\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014\u00a8\u0006#"}, d2 = {"Lgr/tsambala/tutorbilling/ui/home/HomeViewModel$StudentSummary;", "", "id", "", "name", "", "weekTotal", "", "monthTotal", "formattedRate", "lessonCount", "", "(JLjava/lang/String;DDLjava/lang/String;I)V", "getFormattedRate", "()Ljava/lang/String;", "getId", "()J", "getLessonCount", "()I", "getMonthTotal", "()D", "getName", "getWeekTotal", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class StudentSummary {
        private final long id = 0L;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        private final double weekTotal = 0.0;
        private final double monthTotal = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formattedRate = null;
        private final int lessonCount = 0;
        
        public StudentSummary(long id, @org.jetbrains.annotations.NotNull()
        java.lang.String name, double weekTotal, double monthTotal, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedRate, int lessonCount) {
            super();
        }
        
        public final long getId() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        public final double getWeekTotal() {
            return 0.0;
        }
        
        public final double getMonthTotal() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormattedRate() {
            return null;
        }
        
        public final int getLessonCount() {
            return 0;
        }
        
        public final long component1() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        public final double component4() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        public final int component6() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.home.HomeViewModel.StudentSummary copy(long id, @org.jetbrains.annotations.NotNull()
        java.lang.String name, double weekTotal, double monthTotal, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedRate, int lessonCount) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}