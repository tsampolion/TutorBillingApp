package gr.tsambala.tutorbilling.data.model

import gr.tsambala.tutorbilling.data.model.RateTypes
/**
 * Extension function to calculate lesson fee based on student's rate
 */
fun Lesson.calculateFee(student: Student): Double {
    return when (student.rateType) {
        RateTypes.HOURLY -> (durationMinutes / 60.0) * student.rate
        RateTypes.PER_LESSON -> student.rate
        else -> student.rate
    }
}
