package gr.tsambala.tutorbilling.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.database.TutorBillingDatabase
import javax.inject.Singleton

/**
 * DatabaseModule configures how Hilt provides database-related dependencies.
 *
 * Think of this as the instruction manual for our factory manager (Hilt).
 * It tells Hilt exactly how to create each database component and ensures
 * only one instance of the database exists throughout the app's lifetime.
 *
 * @Module tells Hilt this class contains dependency provision instructions
 * @InstallIn(SingletonComponent::class) means these dependencies live as long
 * as the app is running - they're created once and reused everywhere
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides the Room database instance.
     *
     * This is like telling the factory manager: "When anyone asks for a
     * TutorBillingDatabase, here's exactly how to build one."
     *
     * @Provides marks this method as a dependency provider
     * @Singleton ensures only one database instance exists (important for data consistency)
     * @ApplicationContext gives us the app-wide context needed to create the database
     */
    @Provides
    @Singleton
    fun provideTutorBillingDatabase(
        @ApplicationContext context: Context
    ): TutorBillingDatabase {
        return Room.databaseBuilder(
            context,
            TutorBillingDatabase::class.java,
            TutorBillingDatabase.DATABASE_NAME
        )
            // Fallback to destructive migration for now (deletes data if schema changes)
            // In a production app, you'd write proper migrations to preserve user data
            .fallbackToDestructiveMigration()
            // Build the actual database instance
            .build()
    }

    /**
     * Provides the StudentDao instance.
     *
     * Once we have a database, getting a DAO is simple - just ask the database
     * for it. Hilt will use this method whenever a StudentDao is needed.
     *
     * The beauty of dependency injection is that our ViewModels and other
     * classes can just say "I need a StudentDao" and Hilt delivers it,
     * without them knowing anything about how it's created.
     */
    @Provides
    @Singleton
    fun provideStudentDao(database: TutorBillingDatabase): StudentDao {
        return database.studentDao()
    }

    /**
     * Provides the LessonDao instance.
     *
     * Same principle as StudentDao - we're telling Hilt how to get a LessonDao
     * from the database whenever one is needed.
     */
    @Provides
    @Singleton
    fun provideLessonDao(database: TutorBillingDatabase): LessonDao {
        return database.lessonDao()
    }
}

/**
 * Note on Migrations:
 *
 * Currently, we're using fallbackToDestructiveMigration(), which means if we
 * change our database schema (like adding a new column), the database will be
 * deleted and recreated, losing all data.
 *
 * For your v1.0 release, you'll want to implement proper migrations like this:
 *
 * val MIGRATION_1_2 = object : Migration(1, 2) {
 *     override fun migrate(database: SupportSQLiteDatabase) {
 *         database.execSQL("ALTER TABLE students ADD COLUMN email TEXT")
 *     }
 * }
 *
 * Then add it to the database builder:
 * .addMigrations(MIGRATION_1_2)
 *
 * This preserves user data when updating the app - crucial for a good user experience!
 */