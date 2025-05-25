package gr.tsambala.tutorbilling.ui.lesson;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.compose.collectAsStateWithLifecycle;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a2\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001aX\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\u0010\u0011\u001a\u0004\u0018\u00010\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\rH\u0003\u001aP\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\r2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00010\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0003\u001a\u001a\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\r2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0003\u001a\u001a\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0003\u001a\"\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0003\u001a \u0010)\u001a\u00020\u00012\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\'\u001a\u00020(H\u0007\u001a\u0010\u0010+\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#H\u0003\u001a>\u0010,\u001a\u00020\u00012\f\u0010-\u001a\b\u0012\u0004\u0012\u00020#0\u00162\b\u0010.\u001a\u0004\u0018\u00010#2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00010\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0003\u001a2\u00100\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u000b2\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u00a8\u00063"}, d2 = {"DatePickerDialog", "", "selectedDate", "Ljava/time/LocalDate;", "onDateSelected", "Lkotlin/Function1;", "onDismiss", "Lkotlin/Function0;", "DateTimeSection", "date", "startTime", "Ljava/time/LocalTime;", "formattedDate", "", "formattedTimeRange", "onDateClick", "onTimeClick", "dateError", "timeError", "DurationSection", "duration", "quickOptions", "", "", "onDurationChange", "onQuickSelect", "error", "ErrorDisplay", "message", "modifier", "Landroidx/compose/ui/Modifier;", "FeeCalculationCard", "fee", "", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "LessonForm", "uiState", "Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel$LessonUiState;", "viewModel", "Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel;", "LessonScreen", "onNavigateBack", "SelectedStudentCard", "StudentSelectionSection", "students", "selectedStudent", "onStudentSelected", "TimePickerDialog", "selectedTime", "onTimeSelected", "app_debug"})
public final class LessonScreenKt {
    
    /**
     * LessonScreen handles creating and editing lessons.
     *
     * This screen provides an intuitive interface for:
     * - Selecting a student (if not preselected)
     * - Choosing date and time with native pickers
     * - Setting duration with quick-select buttons or custom input
     * - Adding optional notes
     * - Seeing real-time fee calculation
     *
     * The UI adapts based on whether we're creating a new lesson or editing an existing one.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LessonScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.ui.lesson.LessonViewModel viewModel) {
    }
    
    /**
     * Main lesson form containing all input fields.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void LessonForm(gr.tsambala.tutorbilling.ui.lesson.LessonViewModel.LessonUiState uiState, gr.tsambala.tutorbilling.ui.lesson.LessonViewModel viewModel, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Student selection dropdown section.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void StudentSelectionSection(java.util.List<gr.tsambala.tutorbilling.data.model.Student> students, gr.tsambala.tutorbilling.data.model.Student selectedStudent, kotlin.jvm.functions.Function1<? super gr.tsambala.tutorbilling.data.model.Student, kotlin.Unit> onStudentSelected, java.lang.String error) {
    }
    
    /**
     * Selected student display card (read-only).
     */
    @androidx.compose.runtime.Composable()
    private static final void SelectedStudentCard(gr.tsambala.tutorbilling.data.model.Student student) {
    }
    
    /**
     * Date and time selection section.
     */
    @androidx.compose.runtime.Composable()
    private static final void DateTimeSection(java.time.LocalDate date, java.time.LocalTime startTime, java.lang.String formattedDate, java.lang.String formattedTimeRange, kotlin.jvm.functions.Function0<kotlin.Unit> onDateClick, kotlin.jvm.functions.Function0<kotlin.Unit> onTimeClick, java.lang.String dateError, java.lang.String timeError) {
    }
    
    /**
     * Duration input section with quick select buttons.
     */
    @androidx.compose.runtime.Composable()
    private static final void DurationSection(java.lang.String duration, java.util.List<java.lang.Integer> quickOptions, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDurationChange, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onQuickSelect, java.lang.String error) {
    }
    
    /**
     * Fee calculation display card.
     */
    @androidx.compose.runtime.Composable()
    private static final void FeeCalculationCard(double fee, gr.tsambala.tutorbilling.data.model.Student student) {
    }
    
    /**
     * Date picker dialog using Material 3 DatePicker.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void DatePickerDialog(java.time.LocalDate selectedDate, kotlin.jvm.functions.Function1<? super java.time.LocalDate, kotlin.Unit> onDateSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * Time picker dialog using Material 3 TimePicker.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void TimePickerDialog(java.time.LocalTime selectedTime, kotlin.jvm.functions.Function1<? super java.time.LocalTime, kotlin.Unit> onTimeSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * Error display component.
     */
    @androidx.compose.runtime.Composable()
    private static final void ErrorDisplay(java.lang.String message, androidx.compose.ui.Modifier modifier) {
    }
}