package gr.tsambala.tutorbilling.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import gr.tsambala.tutorbilling.data.database.TutorBillingDatabase;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideTutorBillingDatabaseFactory implements Factory<TutorBillingDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideTutorBillingDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TutorBillingDatabase get() {
    return provideTutorBillingDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideTutorBillingDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideTutorBillingDatabaseFactory(contextProvider);
  }

  public static TutorBillingDatabase provideTutorBillingDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTutorBillingDatabase(context));
  }
}
