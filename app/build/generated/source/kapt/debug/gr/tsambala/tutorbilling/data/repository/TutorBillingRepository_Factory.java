package gr.tsambala.tutorbilling.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
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
public final class TutorBillingRepository_Factory implements Factory<TutorBillingRepository> {
  private final Provider<StudentDao> studentDaoProvider;

  private final Provider<LessonDao> lessonDaoProvider;

  public TutorBillingRepository_Factory(Provider<StudentDao> studentDaoProvider,
      Provider<LessonDao> lessonDaoProvider) {
    this.studentDaoProvider = studentDaoProvider;
    this.lessonDaoProvider = lessonDaoProvider;
  }

  @Override
  public TutorBillingRepository get() {
    return newInstance(studentDaoProvider.get(), lessonDaoProvider.get());
  }

  public static TutorBillingRepository_Factory create(Provider<StudentDao> studentDaoProvider,
      Provider<LessonDao> lessonDaoProvider) {
    return new TutorBillingRepository_Factory(studentDaoProvider, lessonDaoProvider);
  }

  public static TutorBillingRepository newInstance(StudentDao studentDao, LessonDao lessonDao) {
    return new TutorBillingRepository(studentDao, lessonDao);
  }
}
