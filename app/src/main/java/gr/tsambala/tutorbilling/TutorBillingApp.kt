package gr.tsambala.tutorbilling

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gr.tsambala.tutorbilling.ui.home.HomeMenuScreen
import gr.tsambala.tutorbilling.ui.students.StudentsScreen
import gr.tsambala.tutorbilling.ui.classes.ClassesScreen
import gr.tsambala.tutorbilling.ui.student.StudentScreen
import gr.tsambala.tutorbilling.ui.lesson.LessonScreen
import gr.tsambala.tutorbilling.ui.lessons.LessonsScreen

@Composable
fun TutorBillingApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeMenuScreen(
                onStudentsClick = { navController.navigate("students") },
                onClassesClick = { navController.navigate("classes") },
                onLessonsClick = { navController.navigate("lessons") },
                onAddStudent = { navController.navigate("student/new") },
                onAddLesson = {
                    navController.navigate("lesson/null/new")
                }
            )
        }

        composable("students") {
            StudentsScreen(
                onNavigateToStudent = { studentId ->
                    navController.navigate("student/$studentId")
                },
                onAddStudent = { navController.navigate("student/new") }
            )
        }

        composable("classes") {
            ClassesScreen(
                onBack = { navController.popBackStack() },
                onStudentClick = { id -> navController.navigate("student/$id") }
            )
        }

        composable("lessons") {
            LessonsScreen(
                onBack = { navController.popBackStack() },
                onLessonClick = { studentId, lessonId ->
                    navController.navigate("lesson/$studentId/$lessonId")
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