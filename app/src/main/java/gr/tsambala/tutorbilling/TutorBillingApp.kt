// TutorBillingApp.kt - Fixed navigation handling
package gr.tsambala.tutorbilling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import gr.tsambala.tutorbilling.ui.home.HomeMenuScreen
import gr.tsambala.tutorbilling.ui.lesson.LessonScreen
import gr.tsambala.tutorbilling.ui.lesson.LessonViewModel
import gr.tsambala.tutorbilling.ui.student.StudentScreen
import gr.tsambala.tutorbilling.ui.student.StudentViewModel

@Composable
fun TutorBillingApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Home Screen
        composable("home") {
            HomeMenuScreen(
                onNavigateToStudent = { studentId ->
                    navController.navigate("student/$studentId")
                },
                onNavigateToNewStudent = {
                    navController.navigate("student/0")
                },
                onNavigateToLesson = { lessonId ->
                    navController.navigate("lesson/$lessonId")
                },
                onNavigateToNewLesson = { studentId ->
                    navController.navigate("lesson/0?studentId=$studentId")
                }
            )
        }

        // Student Detail/Edit Screen
        composable(
            route = "student/{studentId}",
            arguments = listOf(
                navArgument("studentId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val viewModel: StudentViewModel = hiltViewModel()

            // Set up navigation callback
            LaunchedEffect(Unit) {
                viewModel.setNavigationCallback {
                    navController.popBackStack()
                }
            }

            StudentScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Lesson Detail/Edit Screen
        composable(
            route = "lesson/{lessonId}?studentId={studentId}",
            arguments = listOf(
                navArgument("lessonId") {
                    type = NavType.LongType
                },
                navArgument("studentId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { backStackEntry ->
            val viewModel: LessonViewModel = hiltViewModel()

            // Set up navigation callback
            LaunchedEffect(Unit) {
                viewModel.setNavigationCallback {
                    navController.popBackStack()
                }
            }

            LessonScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}