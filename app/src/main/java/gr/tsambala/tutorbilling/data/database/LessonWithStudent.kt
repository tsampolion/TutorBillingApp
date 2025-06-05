package gr.tsambala.tutorbilling.data.database

import androidx.room.Embedded
import androidx.room.Relation
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student
import gr.tsambala.tutorbilling.data.model.RateTypes

data class LessonWithStudent(
    @Embedded val lesson: Lesson,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "id"
    )
    val student: Student
) {
    fun calculateFee(): Double {
        return when (student.rateType) {
            RateTypes.HOURLY -> (lesson.durationMinutes / 60.0) * student.rate
            else -> student.rate
        }
    }
}