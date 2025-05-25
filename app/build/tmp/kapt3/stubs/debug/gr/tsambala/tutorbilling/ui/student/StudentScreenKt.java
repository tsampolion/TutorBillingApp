package gr.tsambala.tutorbilling.ui.student;

import androidx.compose.animation.*;
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
import gr.tsambala.tutorbilling.data.model.RateType;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0012\u0010\u0007\u001a\u00020\u00012\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001a\u001a\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001a(\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001a\u001a\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001aH\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u0018H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a:\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u001e2\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001a.\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00010(2\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001a>\u0010*\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0006\u0010+\u001a\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001a\u001c\u0010/\u001a\u00020\u00012\b\u00100\u001a\u0004\u0018\u0001012\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u001aH\u00102\u001a\u00020\u00012\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00010(2\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00010(2\b\b\u0002\u0010+\u001a\u00020,H\u0007\u001a\u0010\u00106\u001a\u00020\u00032\u0006\u00107\u001a\u00020\u001eH\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00068"}, d2 = {"DeleteConfirmationDialog", "", "studentName", "", "onConfirm", "Lkotlin/Function0;", "onDismiss", "EmptyLessonsState", "modifier", "Landroidx/compose/ui/Modifier;", "ErrorDisplay", "message", "LessonCard", "lessonDisplay", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$LessonDisplay;", "onEdit", "SectionHeader", "title", "StatCard", "label", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "containerColor", "Landroidx/compose/ui/graphics/Color;", "contentColor", "StatCard-VT9Kpxs", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;Landroidx/compose/ui/Modifier;JJ)V", "StatisticsSection", "totalEarnings", "", "totalHours", "totalLessons", "", "currentMonthEarnings", "lastMonthEarnings", "StudentDetailView", "uiState", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel$StudentUiState;", "onEditLesson", "Lkotlin/Function1;", "", "StudentEditForm", "viewModel", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel;", "onSave", "onCancel", "StudentInfoCard", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "StudentScreen", "onNavigateBack", "onNavigateToAddLesson", "onNavigateToEditLesson", "formatCurrency", "amount", "app_debug"})
public final class StudentScreenKt {
    
    /**
     * StudentScreen handles both viewing and editing student information.
     *
     * This screen adapts its UI based on the mode:
     * - View mode: Shows student details, lessons, and statistics
     * - Edit mode: Allows modifying student information
     * - Add mode: Shows empty form for creating a new student
     *
     * The screen follows Material Design 3 guidelines with proper elevation,
     * spacing, and interactive elements.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void StudentScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onNavigateToAddLesson, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onNavigateToEditLesson, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.ui.student.StudentViewModel viewModel) {
    }
    
    /**
     * Student detail view showing information and lessons.
     * This is the default view mode for existing students.
     */
    @androidx.compose.runtime.Composable()
    private static final void StudentDetailView(gr.tsambala.tutorbilling.ui.student.StudentViewModel.StudentUiState uiState, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onEditLesson, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Student information card showing basic details.
     */
    @androidx.compose.runtime.Composable()
    private static final void StudentInfoCard(gr.tsambala.tutorbilling.data.model.Student student, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Statistics section showing earnings and lesson counts.
     */
    @androidx.compose.runtime.Composable()
    private static final void StatisticsSection(double totalEarnings, double totalHours, int totalLessons, double currentMonthEarnings, double lastMonthEarnings, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Section header for organizing content.
     */
    @androidx.compose.runtime.Composable()
    private static final void SectionHeader(java.lang.String title, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Individual lesson card showing lesson details.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void LessonCard(gr.tsambala.tutorbilling.ui.student.StudentViewModel.LessonDisplay lessonDisplay, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Empty state for when no lessons exist.
     */
    @androidx.compose.runtime.Composable()
    private static final void EmptyLessonsState(androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Student edit form for adding or editing student information.
     */
    @androidx.compose.runtime.Composable()
    private static final void StudentEditForm(gr.tsambala.tutorbilling.ui.student.StudentViewModel.StudentUiState uiState, gr.tsambala.tutorbilling.ui.student.StudentViewModel viewModel, kotlin.jvm.functions.Function0<kotlin.Unit> onSave, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Delete confirmation dialog.
     */
    @androidx.compose.runtime.Composable()
    private static final void DeleteConfirmationDialog(java.lang.String studentName, kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * Error display component.
     */
    @androidx.compose.runtime.Composable()
    private static final void ErrorDisplay(java.lang.String message, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Helper function to format currency consistently.
     */
    private static final java.lang.String formatCurrency(double amount) {
        return null;
    }
}