package gr.tsambala.tutorbilling.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.Student;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TutorBillingDatabase is the main database class for the app.
 *
 * Think of this as the blueprint for your entire filing system - it defines
 * what types of records (entities) can be stored and provides access to
 * the librarians (DAOs) who know how to handle those records.
 *
 * @Database annotation tells Room:
 * - Which entities (tables) this database contains
 * - The version number (important for migrations when we change the schema)
 * - Whether to export the schema (useful for version control)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lgr/tsambala/tutorbilling/data/database/TutorBillingDatabase;", "Landroidx/room/RoomDatabase;", "()V", "lessonDao", "Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "studentDao", "Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {gr.tsambala.tutorbilling.data.model.Student.class, gr.tsambala.tutorbilling.data.model.Lesson.class}, version = 1, exportSchema = true)
@androidx.room.TypeConverters(value = {gr.tsambala.tutorbilling.data.database.DateTimeConverters.class})
public abstract class TutorBillingDatabase extends androidx.room.RoomDatabase {
    
    /**
     * Database name constant.
     * The actual database file will be stored as "tutor_billing.db"
     * in the app's private storage.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "tutor_billing_database";
    @org.jetbrains.annotations.NotNull()
    public static final gr.tsambala.tutorbilling.data.database.TutorBillingDatabase.Companion Companion = null;
    
    public TutorBillingDatabase() {
        super();
    }
    
    /**
     * Provides access to student-related database operations.
     * Room will generate the implementation of this abstract method.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract gr.tsambala.tutorbilling.data.dao.StudentDao studentDao();
    
    /**
     * Provides access to lesson-related database operations.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lgr/tsambala/tutorbilling/data/database/TutorBillingDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}