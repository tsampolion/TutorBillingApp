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
import gr.tsambala.tutorbilling.data.database.MIGRATION_1_2
import gr.tsambala.tutorbilling.data.database.MIGRATION_2_3
import gr.tsambala.tutorbilling.data.database.MIGRATION_3_4
import gr.tsambala.tutorbilling.data.database.MIGRATION_4_5
import gr.tsambala.tutorbilling.data.database.MIGRATION_5_6
import gr.tsambala.tutorbilling.data.repository.StudentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTutorBillingDatabase(
        @ApplicationContext context: Context
    ): TutorBillingDatabase {
        return Room.databaseBuilder(
            context,
            TutorBillingDatabase::class.java,
            "tutor_billing_database"
        )
            .addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4,
                MIGRATION_4_5,
                MIGRATION_5_6
            )
            .build()
    }

    @Provides
    fun provideStudentDao(database: TutorBillingDatabase): StudentDao {
        return database.studentDao()
    }

    @Provides
    fun provideLessonDao(database: TutorBillingDatabase): LessonDao {
        return database.lessonDao()
    }

    @Provides
    @Singleton
    fun provideStudentRepository(studentDao: StudentDao): StudentRepository {
        return StudentRepository(studentDao)
    }
}
