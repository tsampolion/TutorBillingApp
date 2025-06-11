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
                onNavigateToStudent = { navController.navigate("students") },
                onClassesClick = { navController.navigate("classes") },
                onNavigateToLesson = { navController.navigate("lessons") },
                onNavigateToNewStudent = { navController.navigate("student/0") },
                onNavigateToNewLesson = { navController.navigate("lesson/0") },
                onRevenue = { navController.navigate("revenue") },
                onSettings = { navController.navigate("settings") }
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

            // Obtain the student id from the nav arguments
            val studentIdArg = backStackEntry.arguments?.getLong("studentId") ?: 0L

            // Set up navigation callback
            LaunchedEffect(Unit) {
                viewModel.setNavigationCallback {
                    navController.popBackStack()
                }
            }

            val studentId = studentIdArg.toString()
            StudentScreen(
                studentId = studentId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLesson = { lessonId ->
                    navController.navigate("lesson/$lessonId?studentId=$studentIdArg")
                },
                onAddLesson = {
                    navController.navigate("lesson/0?studentId=$studentIdArg")
                },
                viewModel = viewModel
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

            val lessonId = backStackEntry.arguments?.getLong("lessonId") ?: 0L
            val studentId = backStackEntry.arguments?.getLong("studentId") ?: 0L

            LessonScreen(
                studentId = studentId.takeIf { it != 0L },
                lessonId = lessonId,
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
