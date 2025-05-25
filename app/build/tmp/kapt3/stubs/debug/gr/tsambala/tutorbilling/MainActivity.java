package gr.tsambala.tutorbilling;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.ui.Modifier;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * MainActivity is the first screen your users see when they open your app.
 * Think of it as the "front door" to your application.
 *
 * The @AndroidEntryPoint annotation tells Hilt that this Activity can receive
 * injected dependencies. This is essential because our screens will use ViewModels
 * that depend on repositories and database connections.
 *
 * ComponentActivity is a modern base class that provides built-in support for
 * Jetpack Compose, lifecycle management, and other modern Android features.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\u0007"}, d2 = {"Lgr/tsambala/tutorbilling/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    
    public MainActivity() {
        super(0);
    }
    
    /**
     * onCreate is called when Android creates this Activity for the first time.
     * This is where we set up the user interface using Jetpack Compose.
     */
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
}