package gr.tsambala.tutorbilling.ui.lesson;

import androidx.lifecycle.SavedStateHandle;
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
public final class LessonViewModel_Factory implements Factory<LessonViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<LessonDao> lessonDaoProvider;

  private final Provider<StudentDao> studentDaoProvider;

  public LessonViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<LessonDao> lessonDaoProvider, Provider<StudentDao> studentDaoProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.lessonDaoProvider = lessonDaoProvider;
    this.studentDaoProvider = studentDaoProvider;
  }

  @Override
  public LessonViewModel get() {
    return newInstance(savedStateHandleProvider.get(), lessonDaoProvider.get(), studentDaoProvider.get());
  }

  public static LessonViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<LessonDao> lessonDaoProvider, Provider<StudentDao> studentDaoProvider) {
    return new LessonViewModel_Factory(savedStateHandleProvider, lessonDaoProvider, studentDaoProvider);
  }

  public static LessonViewModel newInstance(SavedStateHandle savedStateHandle, LessonDao lessonDao,
      StudentDao studentDao) {
    return new LessonViewModel(savedStateHandle, lessonDao, studentDao);
  }
}
