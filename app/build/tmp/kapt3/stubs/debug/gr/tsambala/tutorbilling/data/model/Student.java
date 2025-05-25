package gr.tsambala.tutorbilling.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.Instant;

/**
 * Student entity represents a tutoring student in the database.
 *
 * Think of this as a student's file card in a filing cabinet. It contains
 * all the essential information about a student that we need to track.
 *
 * @Entity tells Room that this class represents a table in our database.
 * Each property becomes a column in that table.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BM\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003J\t\u0010 \u001a\u00020\nH\u00c6\u0003J\t\u0010!\u001a\u00020\rH\u00c6\u0003JX\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001\u00a2\u0006\u0002\u0010#J\u0013\u0010$\u001a\u00020\r2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010&\u001a\u00020\u0005J\u0006\u0010\'\u001a\u00020(J\u0006\u0010)\u001a\u00020\rJ\t\u0010*\u001a\u00020+H\u00d6\u0001J\t\u0010,\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0019\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010\u00a8\u0006-"}, d2 = {"Lgr/tsambala/tutorbilling/data/model/Student;", "", "id", "", "name", "", "hourlyRate", "", "perLessonRate", "createdAt", "Ljava/time/Instant;", "updatedAt", "isActive", "", "(JLjava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/time/Instant;Ljava/time/Instant;Z)V", "getCreatedAt", "()Ljava/time/Instant;", "getHourlyRate", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getId", "()J", "()Z", "getName", "()Ljava/lang/String;", "getPerLessonRate", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(JLjava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/time/Instant;Ljava/time/Instant;Z)Lgr/tsambala/tutorbilling/data/model/Student;", "equals", "other", "getFormattedRate", "getRateType", "Lgr/tsambala/tutorbilling/data/model/RateType;", "hasValidRate", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "students")
public final class Student {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double hourlyRate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double perLessonRate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant createdAt = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant updatedAt = null;
    private final boolean isActive = false;
    
    public Student(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.Double hourlyRate, @org.jetbrains.annotations.Nullable()
    java.lang.Double perLessonRate, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt, @org.jetbrains.annotations.NotNull()
    java.time.Instant updatedAt, boolean isActive) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getHourlyRate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPerLessonRate() {
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
    
    public final boolean isActive() {
        return false;
    }
    
    /**
     * Validation helper to ensure the student has at least one rate type.
     * Returns true if the student has valid rate configuration.
     */
    public final boolean hasValidRate() {
        return false;
    }
    
    /**
     * Helper to determine which rate type this student uses.
     * Useful for UI display logic.
     */
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.model.RateType getRateType() {
        return null;
    }
    
    /**
     * Formats the rate for display with currency symbol.
     * Returns a user-friendly string like "€25.50/hour" or "€30.00/lesson"
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedRate() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.model.Student copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.Double hourlyRate, @org.jetbrains.annotations.Nullable()
    java.lang.Double perLessonRate, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt, @org.jetbrains.annotations.NotNull()
    java.time.Instant updatedAt, boolean isActive) {
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