package gr.tsambala.tutorbilling.ui.home;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.tsambala.tutorbilling.data.model.Student;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import kotlinx.coroutines.flow.*;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010\u0012\u001a\u00020\u000fH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0013"}, d2 = {"Lgr/tsambala/tutorbilling/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "studentDao", "Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "lessonDao", "Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "(Lgr/tsambala/tutorbilling/data/dao/StudentDao;Lgr/tsambala/tutorbilling/data/dao/LessonDao;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lgr/tsambala/tutorbilling/ui/home/HomeUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteStudent", "", "studentId", "", "loadStudentsWithEarnings", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.StudentDao studentDao = null;
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<gr.tsambala.tutorbilling.ui.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.home.HomeUiState> uiState = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.StudentDao studentDao, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<gr.tsambala.tutorbilling.ui.home.HomeUiState> getUiState() {
        return null;
    }
    
    private final void loadStudentsWithEarnings() {
    }
    
    public final void deleteStudent(long studentId) {
    }
}