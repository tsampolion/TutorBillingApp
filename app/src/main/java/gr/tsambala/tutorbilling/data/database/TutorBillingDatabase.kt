package gr.tsambala.tutorbilling.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.model.Lesson

@Database(
    entities = [Student::class, Lesson::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(DateTimeConverters::class)
abstract class TutorBillingDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun lessonDao(): LessonDao
}