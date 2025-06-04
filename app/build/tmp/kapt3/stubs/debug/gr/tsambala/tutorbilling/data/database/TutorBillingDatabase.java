package gr.tsambala.tutorbilling.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.model.Student;
import gr.tsambala.tutorbilling.data.model.Lesson;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lgr/tsambala/tutorbilling/data/database/TutorBillingDatabase;", "Landroidx/room/RoomDatabase;", "()V", "lessonDao", "Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "studentDao", "Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "app_debug"})
@androidx.room.Database(entities = {gr.tsambala.tutorbilling.data.model.Student.class, gr.tsambala.tutorbilling.data.model.Lesson.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters(value = {gr.tsambala.tutorbilling.data.database.DateTimeConverters.class})
public abstract class TutorBillingDatabase extends androidx.room.RoomDatabase {
    
    public TutorBillingDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract gr.tsambala.tutorbilling.data.dao.StudentDao studentDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao();
}