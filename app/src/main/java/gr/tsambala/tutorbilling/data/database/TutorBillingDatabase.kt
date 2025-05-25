package gr.tsambala.tutorbilling.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Student::class, Lesson::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverters::class)
abstract class TutorBillingDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun lessonDao(): LessonDao
}