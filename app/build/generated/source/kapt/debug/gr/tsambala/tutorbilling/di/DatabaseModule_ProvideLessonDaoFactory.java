package gr.tsambala.tutorbilling.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.database.TutorBillingDatabase;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvideLessonDaoFactory implements Factory<LessonDao> {
  private final Provider<TutorBillingDatabase> databaseProvider;

  public DatabaseModule_ProvideLessonDaoFactory(Provider<TutorBillingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public LessonDao get() {
    return provideLessonDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideLessonDaoFactory create(
      Provider<TutorBillingDatabase> databaseProvider) {
    return new DatabaseModule_ProvideLessonDaoFactory(databaseProvider);
  }

  public static LessonDao provideLessonDao(TutorBillingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLessonDao(database));
  }
}
