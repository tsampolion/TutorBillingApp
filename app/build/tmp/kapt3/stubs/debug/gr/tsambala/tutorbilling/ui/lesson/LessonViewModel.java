package gr.tsambala.tutorbilling.ui.lesson;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import kotlinx.coroutines.flow.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0013\u001a\u00020\u0014J\u0014\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\b\u0010\u001a\u001a\u00020\u0016H\u0002J\u0006\u0010\u001b\u001a\u00020\u0016J\u0006\u0010\u001c\u001a\u00020\u0016J\u000e\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\rJ\u000e\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\rJ\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u00162\u0006\u0010$\u001a\u00020\rR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006%"}, d2 = {"Lgr/tsambala/tutorbilling/ui/lesson/LessonViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "lessonDao", "Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "studentDao", "Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "(Landroidx/lifecycle/SavedStateHandle;Lgr/tsambala/tutorbilling/data/dao/LessonDao;Lgr/tsambala/tutorbilling/data/dao/StudentDao;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lgr/tsambala/tutorbilling/ui/lesson/LessonUiState;", "lessonId", "", "studentId", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "calculateFee", "", "deleteLesson", "", "onDeleted", "Lkotlin/Function0;", "loadLesson", "loadStudentInfo", "saveLesson", "toggleEditMode", "updateDate", "date", "updateDuration", "duration", "updateNotes", "notes", "updateStartTime", "time", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class LessonViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao = null;
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.StudentDao studentDao = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String studentId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String lessonId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<gr.tsambala.tutorbilling.ui.lesson.LessonUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.lesson.LessonUiState> uiState = null;
    
    @javax.inject.Inject()
    public LessonViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.StudentDao studentDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.lesson.LessonUiState> getUiState() {
        return null;
    }
    
    private final void loadStudentInfo() {
    }
    
    private final void loadLesson() {
    }
    
    public final void updateDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date) {
    }
    
    public final void updateStartTime(@org.jetbrains.annotations.NotNull()
    java.lang.String time) {
    }
    
    public final void updateDuration(@org.jetbrains.annotations.NotNull()
    java.lang.String duration) {
    }
    
    public final void updateNotes(@org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    public final void toggleEditMode() {
    }
    
    public final void saveLesson() {
    }
    
    public final void deleteLesson(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleted) {
    }
    
    public final double calculateFee() {
        return 0.0;
    }
}