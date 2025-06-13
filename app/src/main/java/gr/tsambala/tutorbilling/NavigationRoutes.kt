package gr.tsambala.tutorbilling

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Students : Screen("students")
    object Student : Screen("student/{studentId}") {
        fun createRoute(studentId: Long) = "student/$studentId"
    }
    object Lessons : Screen("lessons")
    object Lesson : Screen("lesson/{lessonId}?studentId={studentId}") {
        fun createRoute(lessonId: Long, studentId: Long = 0L) =
            "lesson/$lessonId?studentId=$studentId"
    }
    object Classes : Screen("classes")
    object Revenue : Screen("revenue")
    object Invoice : Screen("invoice")
    object PastInvoices : Screen("pastInvoices")
    object Settings : Screen("settings")
}
