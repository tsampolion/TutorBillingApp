package gr.tsambala.tutorbilling.ui.student;

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
public final class StudentViewModel_Factory implements Factory<StudentViewModel> {
  private final Provider<TutorBillingRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public StudentViewModel_Factory(Provider<TutorBillingRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public StudentViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static StudentViewModel_Factory create(Provider<TutorBillingRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new StudentViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static StudentViewModel newInstance(TutorBillingRepository repository,
      SavedStateHandle savedStateHandle) {
    return new StudentViewModel(repository, savedStateHandle);
  }
}
