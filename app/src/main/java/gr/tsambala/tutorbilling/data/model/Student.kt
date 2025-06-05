package gr.tsambala.tutorbilling.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import gr.tsambala.tutorbilling.data.model.RateTypes

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val surname: String,
    val parentMobile: String,
    val parentEmail: String,
    val rateType: String = RateTypes.HOURLY, // "hourly" or "per_lesson"
    val rate: Double,
    val className: String = "Unassigned",
    val isActive: Boolean = true // Added this field that DAOs expect
) {
    fun getFormattedRate(): String {
        return if (rateType == RateTypes.HOURLY) {
            "€%.2f/hour".format(rate)
        } else {
            "€%.2f/lesson".format(rate)
        }
    }

    fun getFullName(): String {
        return "$name $surname".trim()
    }
}