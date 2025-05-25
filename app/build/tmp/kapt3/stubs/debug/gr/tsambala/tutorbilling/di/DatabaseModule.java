package gr.tsambala.tutorbilling.di;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.database.TutorBillingDatabase;
import javax.inject.Singleton;

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
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\f"}, d2 = {"Lgr/tsambala/tutorbilling/di/DatabaseModule;", "", "()V", "provideLessonDao", "Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "database", "Lgr/tsambala/tutorbilling/data/database/TutorBillingDatabase;", "provideStudentDao", "Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "provideTutorBillingDatabase", "context", "Landroid/content/Context;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final gr.tsambala.tutorbilling.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
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
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.database.TutorBillingDatabase provideTutorBillingDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
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
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.dao.StudentDao provideStudentDao(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.database.TutorBillingDatabase database) {
        return null;
    }
    
    /**
     * Provides the LessonDao instance.
     *
     * Same principle as StudentDao - we're telling Hilt how to get a LessonDao
     * from the database whenever one is needed.
     */
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final gr.tsambala.tutorbilling.data.dao.LessonDao provideLessonDao(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.database.TutorBillingDatabase database) {
        return null;
    }
}