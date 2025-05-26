package gr.tsambala.tutorbilling.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentId: Long,
    val date: String,
    val startTime: String,
    val durationMinutes: Int,
    val notes: String? = null
) {
    // This method needs to be added if it doesn't exist
    fun calculateFee(): Double {
        // This is a placeholder - the actual calculation should be done
        // with the student's rate information
        return 0.0
    }
}