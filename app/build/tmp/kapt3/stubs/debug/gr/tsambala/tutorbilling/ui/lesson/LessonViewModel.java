package gr.tsambala.tutorbilling.ui.lesson;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.RateType;
import gr.tsambala.tutorbilling.data.model.Student;
import gr.tsambala.tutorbilling.data.repository.TutorBillingRepository;
import kotlinx.coroutines.flow.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.inject.Inject;

/**
 * LessonViewModel manages the state for lesson-related screens.
 *
 * This ViewModel handles the complex task of coordinating lesson data entry.
 * It's like having a scheduling assistant who not only books appointments
 * but also calculates fees, validates time slots, and ensures everything
 * is properly recorded.
 *
 * The ViewModel handles both creating new lessons and editing existing ones,
 * adapting its behavior based on the navigation parameters it receives.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u000256B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001bJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u001f\u001a\u00020\u001eH\u0002J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\rH\u0002J\u0014\u0010\"\u001a\u00020\u001e2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001e0$J\u000e\u0010%\u001a\u00020\u001e2\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010&\u001a\u00020\u001e2\u0006\u0010\'\u001a\u00020\u0019J\u0006\u0010(\u001a\u00020\u001eJ\u0006\u0010)\u001a\u00020\u001eJ\u000e\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020\u001e2\u0006\u0010.\u001a\u00020\u001bJ\u000e\u0010/\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\u001bJ\u000e\u00101\u001a\u00020\u001e2\u0006\u00102\u001a\u000203J\b\u00104\u001a\u00020\u000bH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000eR\u0012\u0010\u000f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u00067"}, d2 = {"Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository;Landroidx/lifecycle/SavedStateHandle;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$LessonUiState;", "isEditMode", "", "lessonId", "", "Ljava/lang/Long;", "preselectedStudentId", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "calculateFee", "", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "durationMinutes", "", "getFormattedDate", "", "getFormattedTimeRange", "loadLesson", "", "loadStudents", "preselectStudent", "studentId", "saveLesson", "onSuccess", "Lkotlin/Function0;", "selectStudent", "setQuickDuration", "minutes", "toggleDatePicker", "toggleTimePicker", "updateDate", "date", "Ljava/time/LocalDate;", "updateDuration", "durationStr", "updateNotes", "notes", "updateStartTime", "time", "Ljava/time/LocalTime;", "validateInput", "LessonUiState", "ValidationErrors", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class LessonViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.repository.TutorBillingRepository repository = null;
    
    /**
     * Navigation parameters extracted from SavedStateHandle.
     * These determine the mode and context of the screen.
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lessonId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long preselectedStudentId = null;
    
    /**
     * Determines if we're editing an existing lesson or creating a new one.
     */
    private final boolean isEditMode = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.LessonUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.LessonUiState> uiState = null;
    
    @javax.inject.Inject()
    public LessonViewModel(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.repository.TutorBillingRepository repository, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.LessonUiState> getUiState() {
        return null;
    }
    
    /**
     * Loads all active students for the selection dropdown.
     * Students are the foundation of lesson creation - you can't have
     * a lesson without a student to teach!
     */
    private final void loadStudents() {
    }
    
    /**
     * Loads an existing lesson for editing.
     * Populates all form fields with the lesson's current values.
     */
    private final void loadLesson(long lessonId) {
    }
    
    /**
     * Preselects a student when adding a lesson from their detail page.
     * This saves the user a step and provides better UX flow.
     */
    private final void preselectStudent(long studentId) {
    }
    
    /**
     * Selects a student and recalculates the fee.
     * The fee calculation is immediate so users can see the cost as they plan.
     */
    public final void selectStudent(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student) {
    }
    
    /**
     * Updates the lesson date.
     * Future enhancement: could validate against student's availability.
     */
    public final void updateDate(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date) {
    }
    
    /**
     * Updates the start time and recalculates the end time.
     * Times are rounded to the nearest minute for simplicity.
     */
    public final void updateStartTime(@org.jetbrains.annotations.NotNull()
    java.time.LocalTime time) {
    }
    
    /**
     * Updates the duration and recalculates both end time and fee.
     * This is where the magic happens - as users adjust duration,
     * they immediately see how it affects the lesson end time and cost.
     */
    public final void updateDuration(@org.jetbrains.annotations.NotNull()
    java.lang.String durationStr) {
    }
    
    /**
     * Quick duration setter for common lesson lengths.
     * These buttons make it easy to set standard lesson durations.
     */
    public final void setQuickDuration(int minutes) {
    }
    
    /**
     * Updates the optional notes field.
     * Teachers can record topics covered, homework assigned, etc.
     */
    public final void updateNotes(@org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    /**
     * Shows or hides the date picker dialog.
     */
    public final void toggleDatePicker() {
    }
    
    /**
     * Shows or hides the time picker dialog.
     */
    public final void toggleTimePicker() {
    }
    
    /**
     * Calculates the fee based on student rate and duration.
     * This is the core business logic that makes the app valuable.
     */
    private final double calculateFee(gr.tsambala.tutorbilling.data.model.Student student, int durationMinutes) {
        return 0.0;
    }
    
    /**
     * Validates all form inputs before saving.
     * Good validation prevents data errors and provides clear user feedback.
     */
    private final boolean validateInput() {
        return false;
    }
    
    /**
     * Saves the lesson (create new or update existing).
     * This is the culmination of all the data entry - actually persisting the lesson.
     */
    public final void saveLesson(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    /**
     * Formats the date for display in the UI.
     * Using localized formatting for international users.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDate() {
        return null;
    }
    
    /**
     * Formats the time range for display.
     * Shows both start and calculated end time.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedTimeRange() {
        return null;
    }
    
    /**
     * UI state for lesson screens.
     * This comprehensive state covers all aspects of lesson entry and editing.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b1\b\u0086\b\u0018\u00002\u00020\u0001B\u00c1\u0001\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0013\u0012\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0003\u00a2\u0006\u0002\u0010\u001dJ\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0013H\u00c6\u0003J\t\u00108\u001a\u00020\u0013H\u00c6\u0003J\t\u00109\u001a\u00020\u0013H\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\t\u0010;\u001a\u00020\u0018H\u00c6\u0003J\t\u0010<\u001a\u00020\u0013H\u00c6\u0003J\t\u0010=\u001a\u00020\u0013H\u00c6\u0003J\u000f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0003H\u00c6\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u0010\u0010@\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010.J\t\u0010A\u001a\u00020\tH\u00c6\u0003J\t\u0010B\u001a\u00020\u000bH\u00c6\u0003J\t\u0010C\u001a\u00020\rH\u00c6\u0003J\t\u0010D\u001a\u00020\rH\u00c6\u0003J\t\u0010E\u001a\u00020\u0010H\u00c6\u0003J\t\u0010F\u001a\u00020\u000bH\u00c6\u0003J\u00ca\u0001\u0010G\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u000b2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u00132\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u00132\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010HJ\u0013\u0010I\u001a\u00020\u00132\b\u0010J\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010K\u001a\u00020\u001cH\u00d6\u0001J\t\u0010L\u001a\u00020\rH\u00d6\u0001R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0011\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010#R\u0011\u0010\u0015\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\'R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\'R\u0011\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\'R\u0011\u0010\u000e\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010#R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010/\u001a\u0004\b-\u0010.R\u0011\u0010\u0019\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\'R\u0011\u0010\u001a\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\'R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010%R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010*R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00105\u00a8\u0006M"}, d2 = {"Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$LessonUiState;", "", "students", "", "Lgr/tsambala/tutorbilling/data/model/Student;", "selectedStudent", "selectedStudentId", "", "date", "Ljava/time/LocalDate;", "startTime", "Ljava/time/LocalTime;", "durationMinutes", "", "notes", "calculatedFee", "", "endTime", "isLoading", "", "isSaving", "isEditMode", "errorMessage", "validationErrors", "Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$ValidationErrors;", "showDatePicker", "showTimePicker", "quickDurationOptions", "", "(Ljava/util/List;Lgr/tsambala/tutorbilling/data/model/Student;Ljava/lang/Long;Ljava/time/LocalDate;Ljava/time/LocalTime;Ljava/lang/String;Ljava/lang/String;DLjava/time/LocalTime;ZZZLjava/lang/String;Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$ValidationErrors;ZZLjava/util/List;)V", "getCalculatedFee", "()D", "getDate", "()Ljava/time/LocalDate;", "getDurationMinutes", "()Ljava/lang/String;", "getEndTime", "()Ljava/time/LocalTime;", "getErrorMessage", "()Z", "getNotes", "getQuickDurationOptions", "()Ljava/util/List;", "getSelectedStudent", "()Lgr/tsambala/tutorbilling/data/model/Student;", "getSelectedStudentId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getShowDatePicker", "getShowTimePicker", "getStartTime", "getStudents", "getValidationErrors", "()Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$ValidationErrors;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/util/List;Lgr/tsambala/tutorbilling/data/model/Student;Ljava/lang/Long;Ljava/time/LocalDate;Ljava/time/LocalTime;Ljava/lang/String;Ljava/lang/String;DLjava/time/LocalTime;ZZZLjava/lang/String;Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$ValidationErrors;ZZLjava/util/List;)Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$LessonUiState;", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class LessonUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<gr.tsambala.tutorbilling.data.model.Student> students = null;
        @org.jetbrains.annotations.Nullable()
        private final gr.tsambala.tutorbilling.data.model.Student selectedStudent = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long selectedStudentId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.time.LocalDate date = null;
        @org.jetbrains.annotations.NotNull()
        private final java.time.LocalTime startTime = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String durationMinutes = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String notes = null;
        private final double calculatedFee = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.time.LocalTime endTime = null;
        private final boolean isLoading = false;
        private final boolean isSaving = false;
        private final boolean isEditMode = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String errorMessage = null;
        @org.jetbrains.annotations.NotNull()
        private final gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.ValidationErrors validationErrors = null;
        private final boolean showDatePicker = false;
        private final boolean showTimePicker = false;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.Integer> quickDurationOptions = null;
        
        public LessonUiState(@org.jetbrains.annotations.NotNull()
        java.util.List<gr.tsambala.tutorbilling.data.model.Student> students, @org.jetbrains.annotations.Nullable()
        gr.tsambala.tutorbilling.data.model.Student selectedStudent, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedStudentId, @org.jetbrains.annotations.NotNull()
        java.time.LocalDate date, @org.jetbrains.annotations.NotNull()
        java.time.LocalTime startTime, @org.jetbrains.annotations.NotNull()
        java.lang.String durationMinutes, @org.jetbrains.annotations.NotNull()
        java.lang.String notes, double calculatedFee, @org.jetbrains.annotations.NotNull()
        java.time.LocalTime endTime, boolean isLoading, boolean isSaving, boolean isEditMode, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.ValidationErrors validationErrors, boolean showDatePicker, boolean showTimePicker, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.Integer> quickDurationOptions) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<gr.tsambala.tutorbilling.data.model.Student> getStudents() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final gr.tsambala.tutorbilling.data.model.Student getSelectedStudent() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getSelectedStudentId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate getDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalTime getStartTime() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDurationMinutes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNotes() {
            return null;
        }
        
        public final double getCalculatedFee() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalTime getEndTime() {
            return null;
        }
        
        public final boolean isLoading() {
            return false;
        }
        
        public final boolean isSaving() {
            return false;
        }
        
        public final boolean isEditMode() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getErrorMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.ValidationErrors getValidationErrors() {
            return null;
        }
        
        public final boolean getShowDatePicker() {
            return false;
        }
        
        public final boolean getShowTimePicker() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Integer> getQuickDurationOptions() {
            return null;
        }
        
        public LessonUiState() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<gr.tsambala.tutorbilling.data.model.Student> component1() {
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
        public final gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.ValidationErrors component14() {
            return null;
        }
        
        public final boolean component15() {
            return false;
        }
        
        public final boolean component16() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Integer> component17() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final gr.tsambala.tutorbilling.data.model.Student component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalTime component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component7() {
            return null;
        }
        
        public final double component8() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalTime component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.LessonUiState copy(@org.jetbrains.annotations.NotNull()
        java.util.List<gr.tsambala.tutorbilling.data.model.Student> students, @org.jetbrains.annotations.Nullable()
        gr.tsambala.tutorbilling.data.model.Student selectedStudent, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedStudentId, @org.jetbrains.annotations.NotNull()
        java.time.LocalDate date, @org.jetbrains.annotations.NotNull()
        java.time.LocalTime startTime, @org.jetbrains.annotations.NotNull()
        java.lang.String durationMinutes, @org.jetbrains.annotations.NotNull()
        java.lang.String notes, double calculatedFee, @org.jetbrains.annotations.NotNull()
        java.time.LocalTime endTime, boolean isLoading, boolean isSaving, boolean isEditMode, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.ValidationErrors validationErrors, boolean showDatePicker, boolean showTimePicker, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.Integer> quickDurationOptions) {
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
     * Validation errors for form fields.
     * Specific error messages help users correct their input.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J9\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$ValidationErrors;", "", "studentError", "", "dateError", "timeError", "durationError", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDateError", "()Ljava/lang/String;", "getDurationError", "getStudentError", "getTimeError", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ValidationErrors {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String studentError = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String dateError = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String timeError = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String durationError = null;
        
        public ValidationErrors(@org.jetbrains.annotations.Nullable()
        java.lang.String studentError, @org.jetbrains.annotations.Nullable()
        java.lang.String dateError, @org.jetbrains.annotations.Nullable()
        java.lang.String timeError, @org.jetbrains.annotations.Nullable()
        java.lang.String durationError) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getStudentError() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getDateError() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getTimeError() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getDurationError() {
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
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.ValidationErrors copy(@org.jetbrains.annotations.Nullable()
        java.lang.String studentError, @org.jetbrains.annotations.Nullable()
        java.lang.String dateError, @org.jetbrains.annotations.Nullable()
        java.lang.String timeError, @org.jetbrains.annotations.Nullable()
        java.lang.String durationError) {
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