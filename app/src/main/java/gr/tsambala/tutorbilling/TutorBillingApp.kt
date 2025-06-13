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
        startDestination = Screen.Home.route
    ) {
        // Home Screen
        composable(Screen.Home.route) {
            HomeMenuScreen(
                onNavigateToStudent = { navController.navigate(Screen.Students.route) },
                onClassesClick = { navController.navigate(Screen.Classes.route) },
                onNavigateToLesson = { navController.navigate(Screen.Lessons.route) },
                onNavigateToNewStudent = { navController.navigate(Screen.Student.createRoute(0)) },
                onNavigateToNewLesson = { navController.navigate(Screen.Lesson.createRoute("new")) },
                onRevenue = { navController.navigate(Screen.Revenue.route) },
                onSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        // Student Detail/Edit Screen
        composable(
            route = Screen.Student.route,
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
                    navController.navigate(Screen.Lesson.createRoute(lessonId.toString(), studentIdArg))
                },
                onAddLesson = {
                    navController.navigate(Screen.Lesson.createRoute("new", studentIdArg))
                },
                viewModel = viewModel
            )
        }

        // Lesson Detail/Edit Screen
        composable(
            route = Screen.Lesson.route,
            arguments = listOf(
                navArgument("lessonId") {
                    type = NavType.StringType
                },
                navArgument("studentId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { backStackEntry ->
            val viewModel: LessonViewModel = hiltViewModel()

            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "new"
          
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
