package gr.tsambala.tutorbilling.ui.student;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.Student;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import kotlinx.coroutines.flow.*;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0014\u0010\u0016\u001a\u00020\u00132\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u0018J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\b\u0010\u001a\u001a\u00020\u0013H\u0002J\u0006\u0010\u001b\u001a\u00020\u0013J\u0006\u0010\u001c\u001a\u00020\u0013J\u000e\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\rJ\u000e\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\rJ\u000e\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020\rR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006#"}, d2 = {"Lgr/tsambala/tutorbilling/ui/student/StudentViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "studentDao", "Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "lessonDao", "Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "(Landroidx/lifecycle/SavedStateHandle;Lgr/tsambala/tutorbilling/data/dao/StudentDao;Lgr/tsambala/tutorbilling/data/dao/LessonDao;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lgr/tsambala/tutorbilling/ui/student/StudentUiState;", "studentId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteLesson", "", "lessonId", "", "deleteStudent", "onDeleted", "Lkotlin/Function0;", "loadLessons", "loadStudent", "saveStudent", "toggleEditMode", "updateName", "name", "updateRate", "rate", "updateRateType", "rateType", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class StudentViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.StudentDao studentDao = null;
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String studentId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<gr.tsambala.tutorbilling.ui.student.StudentUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.student.StudentUiState> uiState = null;
    
    @javax.inject.Inject()
    public StudentViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.StudentDao studentDao, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.student.StudentUiState> getUiState() {
        return null;
    }
    
    private final void loadStudent() {
    }
    
    private final void loadLessons() {
    }
    
    public final void updateName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updateRateType(@org.jetbrains.annotations.NotNull()
    java.lang.String rateType) {
    }
    
    public final void updateRate(@org.jetbrains.annotations.NotNull()
    java.lang.String rate) {
    }
    
    public final void toggleEditMode() {
    }
    
    public final void saveStudent() {
    }
    
    public final void deleteStudent(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleted) {
    }
    
    public final void deleteLesson(long lessonId) {
    }
}