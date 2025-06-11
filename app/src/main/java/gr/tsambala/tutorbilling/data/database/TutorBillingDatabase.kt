// TutorBillingDatabase.kt - Fixed database configuration
package gr.tsambala.tutorbilling.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gr.tsambala.tutorbilling.BuildConfig
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student

@Database(
    entities = [Student::class, Lesson::class],
    version = 7, // Current version
    exportSchema = true
)
abstract class TutorBillingDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun lessonDao(): LessonDao

    companion object {
        @Volatile
        private var INSTANCE: TutorBillingDatabase? = null

        fun getDatabase(context: Context): TutorBillingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TutorBillingDatabase::class.java,
                    DatabaseConstants.DATABASE_NAME
                )
                    // Add all migrations
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_3_4,
                        MIGRATION_4_5,
                        MIGRATION_5_6
                    )
                    // Fallback to destructive migration only in debug builds
                    .apply {
                        if (BuildConfig.DEBUG) {
                            fallbackToDestructiveMigration()
                        }
                    }
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
