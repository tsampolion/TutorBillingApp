package gr.tsambala.tutorbilling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint

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
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * onCreate is called when Android creates this Activity for the first time.
     * This is where we set up the user interface using Jetpack Compose.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent is where we define our UI using Compose
        // Everything inside this block is written in the declarative Compose style
        setContent {
            // TutorBillingTheme applies our app's visual styling (colors, fonts, etc.)
            TutorBillingTheme {
                // Surface provides a background and handles things like dark/light theme
                Surface(
                    modifier = Modifier.fillMaxSize(), // Take up the entire screen
                    color = MaterialTheme.colorScheme.background // Use theme-appropriate background color
                ) {
                    // TutorBillingApp contains our navigation logic and all screens
                    TutorBillingApp()
                }
            }
        }
    }
}