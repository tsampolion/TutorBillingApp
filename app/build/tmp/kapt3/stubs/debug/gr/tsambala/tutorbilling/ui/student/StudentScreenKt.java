package gr.tsambala.tutorbilling.ui.student;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import gr.tsambala.tutorbilling.data.model.Lesson;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a6\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001aB\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a>\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\u001a\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001aL\u0010\u0016\u001a\u00020\u00012\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0007\u00a8\u0006\u001c"}, d2 = {"EarningsCard", "", "uiState", "Lgr/tsambala/tutorbilling/ui/student/StudentUiState;", "modifier", "Landroidx/compose/ui/Modifier;", "LessonCard", "lesson", "Lgr/tsambala/tutorbilling/data/model/Lesson;", "onLessonClick", "Lkotlin/Function0;", "onDeleteClick", "StudentDetailView", "Lkotlin/Function1;", "", "onDeleteLesson", "StudentEditForm", "viewModel", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel;", "onSave", "onCancel", "StudentInfoCard", "StudentScreen", "studentId", "", "onNavigateBack", "onNavigateToLesson", "onAddLesson", "app_debug"})
public final class StudentScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void StudentScreen(@org.jetbrains.annotations.Nullable()
    java.lang.String studentId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onNavigateToLesson, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddLesson, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.ui.student.StudentViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StudentDetailView(gr.tsambala.tutorbilling.ui.student.StudentUiState uiState, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onLessonClick, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onDeleteLesson, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StudentInfoCard(gr.tsambala.tutorbilling.ui.student.StudentUiState uiState, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EarningsCard(gr.tsambala.tutorbilling.ui.student.StudentUiState uiState, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void LessonCard(gr.tsambala.tutorbilling.data.model.Lesson lesson, kotlin.jvm.functions.Function0<kotlin.Unit> onLessonClick, kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StudentEditForm(gr.tsambala.tutorbilling.ui.student.StudentUiState uiState, gr.tsambala.tutorbilling.ui.student.StudentViewModel viewModel, kotlin.jvm.functions.Function0<kotlin.Unit> onSave, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, androidx.compose.ui.Modifier modifier) {
    }
}