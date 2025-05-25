package gr.tsambala.tutorbilling.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Lesson entity represents a single tutoring session in the database.
 *
 * Think of this as an entry in a teacher's logbook - it records when
 * the lesson happened, how long it lasted, and any notes about the session.
 *
 * The @ForeignKey annotation creates a relationship with the Student table,
 * ensuring that every lesson belongs to a valid student. If a student is
 * deleted, their lessons can be automatically handled based on our rules.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087\b\u0018\u00002\u00020\u0001BO\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0006H\u00c6\u0003J\t\u0010&\u001a\u00020\bH\u00c6\u0003J\t\u0010\'\u001a\u00020\nH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\t\u0010)\u001a\u00020\u000eH\u00c6\u0003J\t\u0010*\u001a\u00020\u000eH\u00c6\u0003J[\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eH\u00c6\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010/\u001a\u000200J\u0006\u00101\u001a\u00020 J\u0006\u00102\u001a\u00020\bJ\u0006\u00103\u001a\u00020\fJ\t\u00104\u001a\u00020\nH\u00d6\u0001J\u0006\u00105\u001a\u00020-J\u0006\u00106\u001a\u00020-J\t\u00107\u001a\u00020\fH\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012\u00a8\u00068"}, d2 = {"Lgr/tsambala/tutorbilling/data/model/Lesson;", "", "id", "", "studentId", "date", "Ljava/time/LocalDate;", "startTime", "Ljava/time/LocalTime;", "durationMinutes", "", "notes", "", "createdAt", "Ljava/time/Instant;", "updatedAt", "(JJLjava/time/LocalDate;Ljava/time/LocalTime;ILjava/lang/String;Ljava/time/Instant;Ljava/time/Instant;)V", "getCreatedAt", "()Ljava/time/Instant;", "getDate", "()Ljava/time/LocalDate;", "getDurationMinutes", "()I", "getId", "()J", "getNotes", "()Ljava/lang/String;", "getStartTime", "()Ljava/time/LocalTime;", "getStudentId", "getUpdatedAt", "calculateFee", "", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "getDateTime", "Ljava/time/LocalDateTime;", "getDurationHours", "getEndTime", "getFormattedDuration", "hashCode", "isInCurrentMonth", "isInCurrentWeek", "toString", "app_debug"})
@androidx.room.Entity(tableName = "lessons", foreignKeys = {@androidx.room.ForeignKey(entity = gr.tsambala.tutorbilling.data.model.Student.class, parentColumns = {"id"}, childColumns = {"studentId"}, onDelete = 2)}, indices = {@androidx.room.Index(value = {"studentId"}), @androidx.room.Index(value = {"date"})})
public final class Lesson {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long studentId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate date = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalTime startTime = null;
    private final int durationMinutes = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant createdAt = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant updatedAt = null;
    
    public Lesson(long id, long studentId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, @org.jetbrains.annotations.NotNull()
    java.time.LocalTime startTime, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt, @org.jetbrains.annotations.NotNull()
    java.time.Instant updatedAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getStudentId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalTime getStartTime() {
        return null;
    }
    
    public final int getDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getUpdatedAt() {
        return null;
    }
    
    /**
     * Calculates the end time of the lesson based on start time and duration.
     * Useful for display and scheduling conflict detection.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalTime getEndTime() {
        return null;
    }
    
    /**
     * Converts duration to hours as a decimal for rate calculations.
     * For example, 90 minutes becomes 1.5 hours.
     */
    public final double getDurationHours() {
        return 0.0;
    }
    
    /**
     * Formats the duration for user-friendly display.
     * Shows hours and minutes like "1h 30m" or just "45m" for shorter lessons.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDuration() {
        return null;
    }
    
    /**
     * Combines date and start time into a single timestamp.
     * Useful for sorting lessons chronologically.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDateTime getDateTime() {
        return null;
    }
    
    /**
     * Calculates the fee for this lesson based on the student's rate.
     * This is a helper method - the actual calculation will be done
     * in the repository or ViewModel where we have access to the student data.
     *
     * @param student The student this lesson belongs to
     * @return The calculated fee, or 0.0 if no valid rate
     */
    public final double calculateFee(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student) {
        return 0.0;
    }
    
    /**
     * Checks if this lesson is in the current week.
     * Used for calculating weekly totals on the home screen.
     */
    public final boolean isInCurrentWeek() {
        return false;
    }
    
    /**
     * Checks if this lesson is in the current month.
     * Used for calculating monthly totals on the home screen.
     */
    public final boolean isInCurrentMonth() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalTime component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.model.Lesson copy(long id, long studentId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, @org.jetbrains.annotations.NotNull()
    java.time.LocalTime startTime, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt, @org.jetbrains.annotations.NotNull()
    java.time.Instant updatedAt) {
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