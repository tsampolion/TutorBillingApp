package gr.tsambala.tutorbilling.ui.student;

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
public final class StudentViewModel_Factory implements Factory<StudentViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<StudentDao> studentDaoProvider;

  private final Provider<LessonDao> lessonDaoProvider;

  public StudentViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<StudentDao> studentDaoProvider, Provider<LessonDao> lessonDaoProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.studentDaoProvider = studentDaoProvider;
    this.lessonDaoProvider = lessonDaoProvider;
  }

  @Override
  public StudentViewModel get() {
    return newInstance(savedStateHandleProvider.get(), studentDaoProvider.get(), lessonDaoProvider.get());
  }

  public static StudentViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<StudentDao> studentDaoProvider, Provider<LessonDao> lessonDaoProvider) {
    return new StudentViewModel_Factory(savedStateHandleProvider, studentDaoProvider, lessonDaoProvider);
  }

  public static StudentViewModel newInstance(SavedStateHandle savedStateHandle,
      StudentDao studentDao, LessonDao lessonDao) {
    return new StudentViewModel(savedStateHandle, studentDao, lessonDao);
  }
}
