package gr.tsambala.tutorbilling.ui.home;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<StudentDao> studentDaoProvider;

  private final Provider<LessonDao> lessonDaoProvider;

  public HomeViewModel_Factory(Provider<StudentDao> studentDaoProvider,
      Provider<LessonDao> lessonDaoProvider) {
    this.studentDaoProvider = studentDaoProvider;
    this.lessonDaoProvider = lessonDaoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(studentDaoProvider.get(), lessonDaoProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<StudentDao> studentDaoProvider,
      Provider<LessonDao> lessonDaoProvider) {
    return new HomeViewModel_Factory(studentDaoProvider, lessonDaoProvider);
  }

  public static HomeViewModel newInstance(StudentDao studentDao, LessonDao lessonDao) {
    return new HomeViewModel(studentDao, lessonDao);
  }
}
