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
import gr.tsambala.tutorbilling.ui.students.StudentsScreen
import gr.tsambala.tutorbilling.ui.classes.ClassesScreen
import gr.tsambala.tutorbilling.ui.lessons.LessonsScreen
import gr.tsambala.tutorbilling.ui.lesson.LessonScreen
import gr.tsambala.tutorbilling.ui.lesson.LessonViewModel
import gr.tsambala.tutorbilling.ui.revenue.RevenueScreen
import gr.tsambala.tutorbilling.ui.invoice.InvoiceScreen
import gr.tsambala.tutorbilling.ui.invoice.PastInvoicesScreen
import gr.tsambala.tutorbilling.ui.settings.SettingsScreen
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

        // Students list screen
        composable(Screen.Students.route) {
            StudentsScreen(
                onNavigateToStudent = { id ->
                    navController.navigate(Screen.Student.createRoute(id))
                },
                onAddStudent = {
                    navController.navigate(Screen.Student.createRoute(0))
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Classes list screen
        composable(Screen.Classes.route) {
            ClassesScreen(
                onBack = { navController.popBackStack() },
                onStudentClick = { id ->
                    navController.navigate(Screen.Student.createRoute(id))
                }
            )
        }

        // Lessons list screen
        composable(Screen.Lessons.route) {
            LessonsScreen(
                onBack = { navController.popBackStack() },
                onLessonClick = { studentId, lessonId ->
                    navController.navigate(
                        Screen.Lesson.createRoute(lessonId.toString(), studentId)
                    )
                }
            )
        }

        // Revenue screen
        composable(Screen.Revenue.route) {
            RevenueScreen(
                onBack = { navController.popBackStack() },
                onInvoice = { navController.navigate(Screen.Invoice.route) },
                onPastInvoices = { navController.navigate(Screen.PastInvoices.route) }
            )
        }

        // Invoice screen
        composable(Screen.Invoice.route) {
            InvoiceScreen(onBack = { navController.popBackStack() })
        }

        // Past invoices screen
        composable(Screen.PastInvoices.route) {
            PastInvoicesScreen(onBack = { navController.popBackStack() })
        }

        // Settings screen
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
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
