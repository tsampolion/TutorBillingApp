package gr.tsambala.tutorbilling.data.dao;

import androidx.room.*;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.Student;
import kotlinx.coroutines.flow.Flow;
import java.time.Instant;
import java.time.LocalDate;

/**
 * LessonDao defines all database operations for lessons.
 *
 * This is like the section of the librarian's manual dedicated to handling
 * lesson records - how to log new lessons, find past lessons, and generate
 * reports on teaching activity and earnings.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ$\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\'J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0012\u001a\u00020\tH\'J,\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\'J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\r0\f2\u0006\u0010\u0012\u001a\u00020\tH\'J$\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\'J\u001e\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\b\b\u0002\u0010\u001c\u001a\u00020\u000fH\'J(\u0010\u001d\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010 \u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006!"}, d2 = {"Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "", "deleteLesson", "", "lesson", "Lgr/tsambala/tutorbilling/data/model/Lesson;", "(Lgr/tsambala/tutorbilling/data/model/Lesson;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteLessonById", "lessonId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLessonsInDateRange", "Lkotlinx/coroutines/flow/Flow;", "", "startDate", "Ljava/time/LocalDate;", "endDate", "getLastLessonDateForStudent", "studentId", "getLessonById", "getLessonCountForStudent", "", "getLessonsForStudent", "getLessonsForStudentInDateRange", "getLessonsWithStudentForStudent", "Lgr/tsambala/tutorbilling/data/dao/LessonWithStudent;", "getLessonsWithStudentsInDateRange", "getTodaysLessons", "today", "getTotalDurationForStudentInRange", "(JLjava/time/LocalDate;Ljava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertLesson", "updateLesson", "app_debug"})
@androidx.room.Dao()
public abstract interface LessonDao {
    
    /**
     * Inserts a new lesson into the database.
     * Returns the auto-generated ID of the new lesson.
     *
     * Like adding a new entry to your teaching logbook.
     */
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertLesson(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Updates an existing lesson's information.
     * Returns the number of rows updated.
     *
     * For when you need to correct the duration or add notes after the fact.
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLesson(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Deletes a lesson from the database.
     * Unlike students, we do hard delete lessons since they don't have
     * dependent data and mistakes can be re-entered.
     */
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteLesson(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Alternative delete by ID when you don't have the full lesson object.
     */
    @androidx.room.Query(value = "DELETE FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteLessonById(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets a single lesson by ID.
     * Useful for editing or viewing details of a specific lesson.
     */
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLessonById(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super gr.tsambala.tutorbilling.data.model.Lesson> $completion);
    
    /**
     * Gets all lessons for a specific student, ordered by date (newest first).
     * This powers the student detail screen showing their lesson history.
     *
     * Flow ensures the UI updates automatically when lessons are added/edited.
     */
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE studentId = :studentId ORDER BY date DESC, startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getLessonsForStudent(long studentId);
    
    /**
     * Gets lessons for a student within a date range.
     * Useful for generating monthly reports or filtering by period.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM lessons \n        WHERE studentId = :studentId \n        AND date >= :startDate \n        AND date <= :endDate \n        ORDER BY date DESC, startTime DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getLessonsForStudentInDateRange(long studentId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate);
    
    /**
     * Gets all lessons across all students for a date range.
     * Useful for calculating total earnings in a period.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM lessons \n        WHERE date >= :startDate \n        AND date <= :endDate \n        ORDER BY date DESC, startTime DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getAllLessonsInDateRange(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate);
    
    /**
     * Gets today's lessons across all students.
     * Helpful for a daily schedule view.
     */
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE date = :today ORDER BY startTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getTodaysLessons(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate today);
    
    /**
     * Counts total lessons for a student.
     * Quick statistic without loading all lesson data.
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM lessons WHERE studentId = :studentId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLessonCountForStudent(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Calculates total duration (in minutes) for a student in a date range.
     * Useful for time-based reporting.
     */
    @androidx.room.Query(value = "\n        SELECT SUM(durationMinutes) \n        FROM lessons \n        WHERE studentId = :studentId \n        AND date >= :startDate \n        AND date <= :endDate\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalDurationForStudentInRange(long studentId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Gets the most recent lesson date for a student.
     * Helpful for showing "last lesson" info or detecting inactive students.
     */
    @androidx.room.Query(value = "SELECT MAX(date) FROM lessons WHERE studentId = :studentId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLastLessonDateForStudent(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.time.LocalDate> $completion);
    
    /**
     * Complex query that joins lessons with students to calculate earnings.
     * This is the heart of the financial calculations - it gets lessons
     * with their associated student data so we can calculate fees.
     *
     * The JOIN operation connects each lesson with its student's rate info.
     */
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT l.* FROM lessons l\n        INNER JOIN students s ON l.studentId = s.id\n        WHERE s.isActive = 1\n        AND l.date >= :startDate \n        AND l.date <= :endDate\n        ORDER BY l.date DESC, l.startTime DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.dao.LessonWithStudent>> getLessonsWithStudentsInDateRange(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate);
    
    /**
     * Gets lessons with student data for a specific student.
     * More efficient than separate queries when you need both.
     */
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT l.* FROM lessons l\n        WHERE l.studentId = :studentId\n        ORDER BY l.date DESC, l.startTime DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.dao.LessonWithStudent>> getLessonsWithStudentForStudent(long studentId);
    
    /**
     * LessonDao defines all database operations for lessons.
     *
     * This is like the section of the librarian's manual dedicated to handling
     * lesson records - how to log new lessons, find past lessons, and generate
     * reports on teaching activity and earnings.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}