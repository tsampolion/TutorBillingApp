package gr.tsambala.tutorbilling.data.dao;

import androidx.room.*;
import gr.tsambala.tutorbilling.data.model.Lesson;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0018\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\f2\u0006\u0010\b\u001a\u00020\tH\'J\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0010\u001a\u00020\tH\'J$\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\'J\u0016\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0017"}, d2 = {"Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "", "delete", "", "lesson", "Lgr/tsambala/tutorbilling/data/model/Lesson;", "(Lgr/tsambala/tutorbilling/data/model/Lesson;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteById", "lessonId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLessons", "Lkotlinx/coroutines/flow/Flow;", "", "getLessonById", "getLessonsByStudentId", "studentId", "getLessonsInDateRange", "startDate", "", "endDate", "insert", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface LessonDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<gr.tsambala.tutorbilling.data.model.Lesson> getLessonById(long lessonId);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE studentId = :studentId ORDER BY date DESC, startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getLessonsByStudentId(long studentId);
    
    @androidx.room.Query(value = "SELECT * FROM lessons ORDER BY date DESC, startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getAllLessons();
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getLessonsInDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String startDate, @org.jetbrains.annotations.NotNull()
    java.lang.String endDate);
}