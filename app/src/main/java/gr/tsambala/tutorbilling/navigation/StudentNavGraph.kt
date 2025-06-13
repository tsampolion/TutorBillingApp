package gr.tsambala.tutorbilling.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import gr.tsambala.tutorbilling.Screen
import gr.tsambala.tutorbilling.ui.student.StudentScreen
import gr.tsambala.tutorbilling.ui.student.StudentViewModel
import gr.tsambala.tutorbilling.ui.students.StudentsScreen

fun NavGraphBuilder.studentGraph(navController: NavHostController) {
    navigation(startDestination = Screen.Students.route, route = "student_graph") {
        composable(Screen.Students.route) {
            StudentsScreen(
                onNavigateToStudent = { id ->
                    navController.navigate(Screen.Student.createRoute(id))
                },
                onAddStudent = {
                    navController.navigate(Screen.Student.createRoute(0L))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Student.route,
            arguments = listOf(
                navArgument("studentId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val viewModel: StudentViewModel = hiltViewModel()

            val studentIdArg = backStackEntry.arguments?.getLong("studentId") ?: 0L

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
                    navController.navigate(
                        Screen.Lesson.createRoute(lessonId.toString(), studentIdArg)
                    )
                },
                onAddLesson = {
                    navController.navigate(Screen.Lesson.createRoute("new", studentIdArg))
                },
                viewModel = viewModel
            )
        }
    }
}
