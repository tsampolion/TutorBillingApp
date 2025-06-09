package gr.tsambala.tutorbilling.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.model.Lesson

@Database(
    entities = [Student::class, Lesson::class],
    version = 6,
    exportSchema = false
)
abstract class TutorBillingDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun lessonDao(): LessonDao
}
