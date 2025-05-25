package gr.tsambala.tutorbilling.ui.student;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.RateType;
import gr.tsambala.tutorbilling.data.model.Student;
import gr.tsambala.tutorbilling.data.repository.TutorBillingRepository;
import kotlinx.coroutines.flow.*;
import java.time.LocalDate;
import javax.inject.Inject;

/**
 * StudentViewModel manages the state for student-related screens.
 *
 * This ViewModel is more complex than HomeViewModel because it handles multiple
 * responsibilities:
 * 1. Displaying student details and their lessons
 * 2. Adding new students
 * 3. Editing existing students
 * 4. Deleting students (with safety checks)
 *
 * It's like having a specialized clerk who handles all paperwork for a
 * specific student - they can show you the records, update them, or create
 * new ones as needed.
 *
 * SavedStateHandle allows us to receive navigation arguments and survive
 * process death (when Android kills the app to free memory).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001:\u0003*+,B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0016J\u0006\u0010\u0017\u001a\u00020\u0014J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\u000e\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\rJ\u0014\u0010\u001e\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0016J\u0006\u0010\u001f\u001a\u00020\u0014J\u0006\u0010 \u001a\u00020\u0014J\u000e\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u001bJ\u000e\u0010#\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u001bJ\u000e\u0010$\u001a\u00020\u00142\u0006\u0010%\u001a\u00020&J\u000e\u0010\'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020\u001bJ\b\u0010)\u001a\u00020\u000bH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006-"}, d2 = {"Lgr/tsambala/tutorbilling/ui/student/StudentViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository;Landroidx/lifecycle/SavedStateHandle;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$StudentUiState;", "isEditMode", "", "studentId", "", "Ljava/lang/Long;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteStudent", "", "onSuccess", "Lkotlin/Function0;", "hideDeleteConfirmation", "loadLessons", "loadStudent", "onAddLessonClick", "", "onEditLessonClick", "lessonId", "saveStudent", "showDeleteConfirmation", "toggleEditMode", "updateHourlyRate", "rate", "updatePerLessonRate", "updateRateType", "rateType", "Lgr/tsambala/tutorbilling/data/model/RateType;", "updateStudentName", "name", "validateInput", "LessonDisplay", "StudentUiState", "ValidationErrors", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class StudentViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.repository.TutorBillingRepository repository = null;
    
    /**
     * Navigation argument for student ID.
     * If null, we're in "add new student" mode.
     * If present, we're viewing/editing an existing student.
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long studentId = null;
    
    /**
     * Determines if we're in edit mode vs. view mode.
     * Starts as true when adding a new student.
     */
    private boolean isEditMode;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<gr.tsambala.tutorbilling.ui.student.StudentViewModel.StudentUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.student.StudentViewModel.StudentUiState> uiState = null;
    
    @javax.inject.Inject()
    public StudentViewModel(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.repository.TutorBillingRepository repository, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.student.StudentViewModel.StudentUiState> getUiState() {
        return null;
    }
    
    /**
     * Loads student data from the repository.
     * Sets up reactive updates so any changes to the student are reflected immediately.
     */
    private final void loadStudent(long studentId) {
    }
    
    /**
     * Loads lessons for the student.
     * Calculates various statistics and formats data for display.
     */
    private final void loadLessons(long studentId) {
    }
    
    /**
     * Updates the student's name in the form.
     */
    public final void updateStudentName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    /**
     * Updates the rate type (hourly vs. per lesson).
     */
    public final void updateRateType(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.RateType rateType) {
    }
    
    /**
     * Updates the hourly rate.
     */
    public final void updateHourlyRate(@org.jetbrains.annotations.NotNull()
    java.lang.String rate) {
    }
    
    /**
     * Updates the per-lesson rate.
     */
    public final void updatePerLessonRate(@org.jetbrains.annotations.NotNull()
    java.lang.String rate) {
    }
    
    /**
     * Toggles between view and edit mode.
     */
    public final void toggleEditMode() {
    }
    
    /**
     * Validates form input and returns true if valid.
     * Updates validation error messages in the UI state.
     */
    private final boolean validateInput() {
        return false;
    }
    
    /**
     * Saves the student (either creating new or updating existing).
     * Returns true if successful, false otherwise.
     */
    public final void saveStudent(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    /**
     * Shows the delete confirmation dialog.
     */
    public final void showDeleteConfirmation() {
    }
    
    /**
     * Hides the delete confirmation dialog.
     */
    public final void hideDeleteConfirmation() {
    }
    
    /**
     * Deletes the student.
     */
    public final void deleteStudent(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    /**
     * Navigates to add a lesson for this student.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String onAddLessonClick() {
        return null;
    }
    
    /**
     * Navigates to edit a lesson.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String onEditLessonClick(long lessonId) {
        return null;
    }
    
    /**
     * Lesson data formatted for display.
     * Includes calculated values to avoid recalculation in the UI.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001f"}, d2 = {"Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$LessonDisplay;", "", "lesson", "Lgr/tsambala/tutorbilling/data/model/Lesson;", "fee", "", "formattedDate", "", "formattedTime", "formattedDuration", "(Lgr/tsambala/tutorbilling/data/model/Lesson;DLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getFee", "()D", "getFormattedDate", "()Ljava/lang/String;", "getFormattedDuration", "getFormattedTime", "getLesson", "()Lgr/tsambala/tutorbilling/data/model/Lesson;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class LessonDisplay {
        @org.jetbrains.annotations.NotNull()
        private final gr.tsambala.tutorbilling.data.model.Lesson lesson = null;
        private final double fee = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formattedDate = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formattedTime = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formattedDuration = null;
        
        public LessonDisplay(@org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.Lesson lesson, double fee, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedDate, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedTime, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedDuration) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.Lesson getLesson() {
            return null;
        }
        
        public final double getFee() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormattedDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormattedTime() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormattedDuration() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.Lesson component1() {
            return null;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.student.StudentViewModel.LessonDisplay copy(@org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.Lesson lesson, double fee, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedDate, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedTime, @org.jetbrains.annotations.NotNull()
        java.lang.String formattedDuration) {
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
     * UI state for student screens.
     * This comprehensive state object contains everything needed for:
     * - Student detail view
     * - Add student form
     * - Edit student form
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b4\b\u0086\b\u0018\u00002\u00020\u0001B\u00c3\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u001dJ\u000b\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0013H\u00c6\u0003J\t\u00107\u001a\u00020\u0013H\u00c6\u0003J\t\u00108\u001a\u00020\u0013H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u0018H\u00c6\u0003J\t\u0010;\u001a\u00020\u000eH\u00c6\u0003J\t\u0010<\u001a\u00020\u000eH\u00c6\u0003J\t\u0010=\u001a\u00020\u0013H\u00c6\u0003J\t\u0010>\u001a\u00020\u0013H\u00c6\u0003J\t\u0010?\u001a\u00020\u0005H\u00c6\u0003J\t\u0010@\u001a\u00020\u0007H\u00c6\u0003J\t\u0010A\u001a\u00020\u0005H\u00c6\u0003J\t\u0010B\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010C\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003J\t\u0010D\u001a\u00020\u000eH\u00c6\u0003J\t\u0010E\u001a\u00020\u000eH\u00c6\u0003J\t\u0010F\u001a\u00020\u0011H\u00c6\u0003J\u00c7\u0001\u0010G\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u00132\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u000e2\b\b\u0002\u0010\u001a\u001a\u00020\u000e2\b\b\u0002\u0010\u001b\u001a\u00020\u00132\b\b\u0002\u0010\u001c\u001a\u00020\u0013H\u00c6\u0001J\u0013\u0010H\u001a\u00020\u00132\b\u0010I\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010J\u001a\u00020\u0011H\u00d6\u0001J\t\u0010K\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u001c\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0019\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010#R\u0011\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u001fR\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u001fR\u0011\u0010\u0015\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u001fR\u0011\u0010\u001a\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010!R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010#R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u001b\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010#R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010!R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010!R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104\u00a8\u0006L"}, d2 = {"Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$StudentUiState;", "", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "studentName", "", "rateType", "Lgr/tsambala/tutorbilling/data/model/RateType;", "hourlyRate", "perLessonRate", "lessons", "", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$LessonDisplay;", "totalEarnings", "", "totalHours", "totalLessons", "", "isLoading", "", "isEditMode", "isSaving", "errorMessage", "validationErrors", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$ValidationErrors;", "currentMonthEarnings", "lastMonthEarnings", "showDeleteConfirmation", "canDelete", "(Lgr/tsambala/tutorbilling/data/model/Student;Ljava/lang/String;Lgr/tsambala/tutorbilling/data/model/RateType;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;DDIZZZLjava/lang/String;Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$ValidationErrors;DDZZ)V", "getCanDelete", "()Z", "getCurrentMonthEarnings", "()D", "getErrorMessage", "()Ljava/lang/String;", "getHourlyRate", "getLastMonthEarnings", "getLessons", "()Ljava/util/List;", "getPerLessonRate", "getRateType", "()Lgr/tsambala/tutorbilling/data/model/RateType;", "getShowDeleteConfirmation", "getStudent", "()Lgr/tsambala/tutorbilling/data/model/Student;", "getStudentName", "getTotalEarnings", "getTotalHours", "getTotalLessons", "()I", "getValidationErrors", "()Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$ValidationErrors;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class StudentUiState {
        @org.jetbrains.annotations.Nullable()
        private final gr.tsambala.tutorbilling.data.model.Student student = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String studentName = null;
        @org.jetbrains.annotations.NotNull()
        private final gr.tsambala.tutorbilling.data.model.RateType rateType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String hourlyRate = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String perLessonRate = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<gr.tsambala.tutorbilling.ui.student.StudentViewModel.LessonDisplay> lessons = null;
        private final double totalEarnings = 0.0;
        private final double totalHours = 0.0;
        private final int totalLessons = 0;
        private final boolean isLoading = false;
        private final boolean isEditMode = false;
        private final boolean isSaving = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String errorMessage = null;
        @org.jetbrains.annotations.NotNull()
        private final gr.tsambala.tutorbilling.ui.student.StudentViewModel.ValidationErrors validationErrors = null;
        private final double currentMonthEarnings = 0.0;
        private final double lastMonthEarnings = 0.0;
        private final boolean showDeleteConfirmation = false;
        private final boolean canDelete = false;
        
        public StudentUiState(@org.jetbrains.annotations.Nullable()
        gr.tsambala.tutorbilling.data.model.Student student, @org.jetbrains.annotations.NotNull()
        java.lang.String studentName, @org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.RateType rateType, @org.jetbrains.annotations.NotNull()
        java.lang.String hourlyRate, @org.jetbrains.annotations.NotNull()
        java.lang.String perLessonRate, @org.jetbrains.annotations.NotNull()
        java.util.List<gr.tsambala.tutorbilling.ui.student.StudentViewModel.LessonDisplay> lessons, double totalEarnings, double totalHours, int totalLessons, boolean isLoading, boolean isEditMode, boolean isSaving, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.ui.student.StudentViewModel.ValidationErrors validationErrors, double currentMonthEarnings, double lastMonthEarnings, boolean showDeleteConfirmation, boolean canDelete) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final gr.tsambala.tutorbilling.data.model.Student getStudent() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getStudentName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.RateType getRateType() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getHourlyRate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPerLessonRate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<gr.tsambala.tutorbilling.ui.student.StudentViewModel.LessonDisplay> getLessons() {
            return null;
        }
        
        public final double getTotalEarnings() {
            return 0.0;
        }
        
        public final double getTotalHours() {
            return 0.0;
        }
        
        public final int getTotalLessons() {
            return 0;
        }
        
        public final boolean isLoading() {
            return false;
        }
        
        public final boolean isEditMode() {
            return false;
        }
        
        public final boolean isSaving() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getErrorMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.student.StudentViewModel.ValidationErrors getValidationErrors() {
            return null;
        }
        
        public final double getCurrentMonthEarnings() {
            return 0.0;
        }
        
        public final double getLastMonthEarnings() {
            return 0.0;
        }
        
        public final boolean getShowDeleteConfirmation() {
            return false;
        }
        
        public final boolean getCanDelete() {
            return false;
        }
        
        public StudentUiState() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final gr.tsambala.tutorbilling.data.model.Student component1() {
            return null;
        }
        
        public final boolean component10() {
            return false;
        }
        
        public final boolean component11() {
            return false;
        }
        
        public final boolean component12() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component13() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.student.StudentViewModel.ValidationErrors component14() {
            return null;
        }
        
        public final double component15() {
            return 0.0;
        }
        
        public final double component16() {
            return 0.0;
        }
        
        public final boolean component17() {
            return false;
        }
        
        public final boolean component18() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.RateType component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<gr.tsambala.tutorbilling.ui.student.StudentViewModel.LessonDisplay> component6() {
            return null;
        }
        
        public final double component7() {
            return 0.0;
        }
        
        public final double component8() {
            return 0.0;
        }
        
        public final int component9() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.student.StudentViewModel.StudentUiState copy(@org.jetbrains.annotations.Nullable()
        gr.tsambala.tutorbilling.data.model.Student student, @org.jetbrains.annotations.NotNull()
        java.lang.String studentName, @org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.RateType rateType, @org.jetbrains.annotations.NotNull()
        java.lang.String hourlyRate, @org.jetbrains.annotations.NotNull()
        java.lang.String perLessonRate, @org.jetbrains.annotations.NotNull()
        java.util.List<gr.tsambala.tutorbilling.ui.student.StudentViewModel.LessonDisplay> lessons, double totalEarnings, double totalHours, int totalLessons, boolean isLoading, boolean isEditMode, boolean isSaving, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.ui.student.StudentViewModel.ValidationErrors validationErrors, double currentMonthEarnings, double lastMonthEarnings, boolean showDeleteConfirmation, boolean canDelete) {
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
     * Validation errors for the form fields.
     * Keeping these separate makes it easy to show field-specific errors.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\u000b\u0010\t\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J!\u0010\u000b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$ValidationErrors;", "", "nameError", "", "rateError", "(Ljava/lang/String;Ljava/lang/String;)V", "getNameError", "()Ljava/lang/String;", "getRateError", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ValidationErrors {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String nameError = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String rateError = null;
        
        public ValidationErrors(@org.jetbrains.annotations.Nullable()
        java.lang.String nameError, @org.jetbrains.annotations.Nullable()
        java.lang.String rateError) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getNameError() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getRateError() {
            return null;
        }
        
        public ValidationErrors() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.student.StudentViewModel.ValidationErrors copy(@org.jetbrains.annotations.Nullable()
        java.lang.String nameError, @org.jetbrains.annotations.Nullable()
        java.lang.String rateError) {
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