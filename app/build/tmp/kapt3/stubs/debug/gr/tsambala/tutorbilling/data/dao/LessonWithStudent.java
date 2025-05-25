package gr.tsambala.tutorbilling.data.dao;

import androidx.room.*;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.Student;
import kotlinx.coroutines.flow.Flow;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Data class that combines a lesson with its associated student.
 * This is like having both the lesson logbook entry and the student's
 * rate card together, so we can calculate earnings.
 *
 * @Embedded tells Room to include all fields from both objects
 * in the query result.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fJ\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0017"}, d2 = {"Lgr/tsambala/tutorbilling/data/dao/LessonWithStudent;", "", "lesson", "Lgr/tsambala/tutorbilling/data/model/Lesson;", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "(Lgr/tsambala/tutorbilling/data/model/Lesson;Lgr/tsambala/tutorbilling/data/model/Student;)V", "getLesson", "()Lgr/tsambala/tutorbilling/data/model/Lesson;", "getStudent", "()Lgr/tsambala/tutorbilling/data/model/Student;", "calculateFee", "", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class LessonWithStudent {
    @androidx.room.Embedded()
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.model.Lesson lesson = null;
    @androidx.room.Relation(parentColumn = "studentId", entityColumn = "id")
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.model.Student student = null;
    
    public LessonWithStudent(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.model.Lesson getLesson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.model.Student getStudent() {
        return null;
    }
    
    /**
     * Convenience method to calculate the fee for this lesson.
     * Delegates to the lesson's calculation method with the student data.
     */
    public final double calculateFee() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.model.Lesson component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.model.Student component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.dao.LessonWithStudent copy(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student) {
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