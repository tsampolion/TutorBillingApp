package gr.tsambala.tutorbilling

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gr.tsambala.tutorbilling.ui.home.HomeScreen
import gr.tsambala.tutorbilling.ui.student.StudentScreen
import gr.tsambala.tutorbilling.ui.lesson.LessonScreen

@Composable
fun TutorBillingApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToStudent = { studentId ->
                    navController.navigate("student/$studentId")
                },
                onAddStudent = {
                    navController.navigate("student/new")
                }
            )
        }

        composable("student/{studentId}") { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")
            StudentScreen(
                studentId = studentId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLesson = { lessonId ->
                    navController.navigate("lesson/$studentId/$lessonId")
                },
                onAddLesson = {
                    navController.navigate("lesson/$studentId/new")
                }
            )
        }

        composable("lesson/{studentId}/{lessonId}") { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")
            val lessonId = backStackEntry.arguments?.getString("lessonId")
            LessonScreen(
                studentId = studentId,
                lessonId = lessonId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}