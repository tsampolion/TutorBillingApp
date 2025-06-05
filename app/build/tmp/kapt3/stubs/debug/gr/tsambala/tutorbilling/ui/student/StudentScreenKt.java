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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a4\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a6\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0003\u001a>\u0010\u0012\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0003\u001aL\u0010\u0015\u001a\u00020\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u001b"}, d2 = {"LessonCard", "", "lesson", "Lgr/tsambala/tutorbilling/data/model/Lesson;", "fee", "", "onLessonClick", "Lkotlin/Function0;", "onDeleteClick", "StudentDetailView", "uiState", "Lgr/tsambala/tutorbilling/ui/student/StudentUiState;", "viewModel", "Lgr/tsambala/tutorbilling/ui/student/StudentViewModel;", "Lkotlin/Function1;", "", "modifier", "Landroidx/compose/ui/Modifier;", "StudentEditForm", "onSave", "onCancel", "StudentScreen", "studentId", "", "onNavigateBack", "onNavigateToLesson", "onAddLesson", "app_debug"})
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
    private static final void StudentDetailView(gr.tsambala.tutorbilling.ui.student.StudentUiState uiState, gr.tsambala.tutorbilling.ui.student.StudentViewModel viewModel, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onLessonClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void LessonCard(gr.tsambala.tutorbilling.data.model.Lesson lesson, double fee, kotlin.jvm.functions.Function0<kotlin.Unit> onLessonClick, kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StudentEditForm(gr.tsambala.tutorbilling.ui.student.StudentUiState uiState, gr.tsambala.tutorbilling.ui.student.StudentViewModel viewModel, kotlin.jvm.functions.Function0<kotlin.Unit> onSave, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, androidx.compose.ui.Modifier modifier) {
    }
}