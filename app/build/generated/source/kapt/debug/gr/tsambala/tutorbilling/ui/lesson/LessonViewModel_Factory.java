package gr.tsambala.tutorbilling.ui.lesson;

import androidx.lifecycle.SavedStateHandle;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import gr.tsambala.tutorbilling.data.repository.TutorBillingRepository;
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
  private final Provider<TutorBillingRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public LessonViewModel_Factory(Provider<TutorBillingRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public LessonViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static LessonViewModel_Factory create(Provider<TutorBillingRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new LessonViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static LessonViewModel newInstance(TutorBillingRepository repository,
      SavedStateHandle savedStateHandle) {
    return new LessonViewModel(repository, savedStateHandle);
  }
}
