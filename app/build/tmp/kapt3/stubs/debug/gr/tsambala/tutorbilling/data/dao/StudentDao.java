package gr.tsambala.tutorbilling.data.dao;

import androidx.room.*;
import gr.tsambala.tutorbilling.data.model.Student;
import kotlinx.coroutines.flow.Flow;
import java.time.Instant;

/**
 * StudentDao (Data Access Object) defines all database operations for students.
 *
 * Think of this as a librarian's instruction manual - it contains all the
 * approved ways to add, find, update, and remove student records from our database.
 *
 * The @Dao annotation tells Room that this interface contains database operations.
 * Room will generate the actual implementation code for us at compile time.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J\u0018\u0010\n\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00070\u0006H\'J\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\'J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u001e"}, d2 = {"Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "", "getActiveStudentCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllActiveStudents", "Lkotlinx/coroutines/flow/Flow;", "", "Lgr/tsambala/tutorbilling/data/model/Student;", "getAllStudentsIncludingInactive", "getStudentById", "studentId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStudentsWithLessonCount", "Lgr/tsambala/tutorbilling/data/dao/StudentWithLessonCount;", "insertStudent", "student", "(Lgr/tsambala/tutorbilling/data/model/Student;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchStudentsByName", "searchQuery", "", "softDeleteStudent", "", "timestamp", "Ljava/time/Instant;", "(JLjava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "studentHasLessons", "", "updateStudent", "app_debug"})
@androidx.room.Dao()
public abstract interface StudentDao {
    
    /**
     * Inserts a new student into the database.
     * Returns the auto-generated ID of the new student.
     *
     * @Insert is like filing a new student card in the cabinet.
     * The suspend keyword means this operation happens asynchronously,
     * preventing the UI from freezing while we save data.
     */
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertStudent(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Updates an existing student's information.
     * Returns the number of rows updated (should be 1 if successful).
     *
     * @Update is like pulling out a student's card, making changes,
     * and putting it back in the same spot.
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateStudent(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Soft deletes a student by marking them as inactive.
     * We don't actually remove the data - just hide it from normal views.
     * This preserves historical lesson data.
     *
     * @Query lets us write custom SQL when the built-in operations aren't enough.
     */
    @androidx.room.Query(value = "UPDATE students SET isActive = 0, updatedAt = :timestamp WHERE id = :studentId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDeleteStudent(long studentId, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets a single student by their ID.
     * Returns null if no student found with that ID.
     *
     * This is like asking the librarian to fetch a specific student's file.
     */
    @androidx.room.Query(value = "SELECT * FROM students WHERE id = :studentId AND isActive = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStudentById(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super gr.tsambala.tutorbilling.data.model.Student> $completion);
    
    /**
     * Gets all active students as a Flow.
     * Flow is like a subscription - whenever the data changes,
     * observers are automatically notified with the new data.
     *
     * This powers our home screen list, keeping it always up-to-date.
     */
    @androidx.room.Query(value = "SELECT * FROM students WHERE isActive = 1 ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Student>> getAllActiveStudents();
    
    /**
     * Gets all students including inactive ones.
     * Useful for administrative views or data recovery.
     */
    @androidx.room.Query(value = "SELECT * FROM students ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Student>> getAllStudentsIncludingInactive();
    
    /**
     * Searches for students by name.
     * The % symbols in SQL are wildcards, so searching for "John"
     * would find "John Smith", "Johnny", and "Johnson".
     */
    @androidx.room.Query(value = "SELECT * FROM students WHERE isActive = 1 AND name LIKE \'%\' || :searchQuery || \'%\' ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Student>> searchStudentsByName(@org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery);
    
    /**
     * Counts the total number of active students.
     * Useful for statistics or limiting features in a free version.
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM students WHERE isActive = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getActiveStudentCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Checks if a student has any lessons.
     * Important for determining if we can safely delete a student
     * or if we should warn about losing lesson data.
     */
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM lessons WHERE studentId = :studentId LIMIT 1)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object studentHasLessons(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    /**
     * Gets students with their lesson count.
     * This is a more complex query that joins data from two tables.
     * We'll use this for showing lesson counts on the home screen.
     */
    @androidx.room.Query(value = "\n        SELECT s.*, COUNT(l.id) as lessonCount \n        FROM students s \n        LEFT JOIN lessons l ON s.id = l.studentId \n        WHERE s.isActive = 1 \n        GROUP BY s.id \n        ORDER BY s.name ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.dao.StudentWithLessonCount>> getStudentsWithLessonCount();
}