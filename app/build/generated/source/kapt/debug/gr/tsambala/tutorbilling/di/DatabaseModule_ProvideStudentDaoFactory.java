package gr.tsambala.tutorbilling.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.database.TutorBillingDatabase;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideStudentDaoFactory implements Factory<StudentDao> {
  private final Provider<TutorBillingDatabase> databaseProvider;

  public DatabaseModule_ProvideStudentDaoFactory(Provider<TutorBillingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StudentDao get() {
    return provideStudentDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStudentDaoFactory create(
      Provider<TutorBillingDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStudentDaoFactory(databaseProvider);
  }

  public static StudentDao provideStudentDao(TutorBillingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStudentDao(database));
  }
}
