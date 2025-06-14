package gr.tsambala.tutorbilling.ui.revenue

import androidx.compose.runtime.Stable
import gr.tsambala.tutorbilling.data.model.Student

@Stable
data class StudentDebt(
    val student: Student,
    val amount: Double
)
