package gr.tsambala.tutorbilling.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val rateType: String = "hourly", // "hourly" or "per_lesson"
    val rate: Double,
    val isActive: Boolean = true // Added this field that DAOs expect
) {
    fun getFormattedRate(): String {
        return if (rateType == "hourly") {
            "€%.2f/hour".format(rate)
        } else {
            "€%.2f/lesson".format(rate)
        }
    }
}