package gr.tsambala.tutorbilling.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

/**
 * Student entity represents a tutoring student in the database.
 *
 * Think of this as a student's file card in a filing cabinet. It contains
 * all the essential information about a student that we need to track.
 *
 * @Entity tells Room that this class represents a table in our database.
 * Each property becomes a column in that table.
 */
@Entity(tableName = "students")
data class Student(
    // Every student needs a unique ID, like a student number
    // Room will auto-generate these IDs for us
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // The student's name - required for identification
    val name: String,

    // How much the student pays per hour of tutoring
    // Stored as Double for decimal precision (e.g., 25.50)
    val hourlyRate: Double? = null,

    // Alternative: fixed rate per lesson regardless of duration
    // A student can have either hourlyRate OR perLessonRate, not both
    val perLessonRate: Double? = null,

    // When this student record was created
    // Useful for sorting and historical tracking
    val createdAt: Instant = Instant.now(),

    // When this record was last modified
    // Automatically updated whenever we edit the student
    val updatedAt: Instant = Instant.now(),

    // Soft delete flag - instead of actually deleting, we mark as inactive
    // This preserves historical data while hiding deleted students
    val isActive: Boolean = true
) {
    /**
     * Validation helper to ensure the student has at least one rate type.
     * Returns true if the student has valid rate configuration.
     */
    fun hasValidRate(): Boolean {
        return hourlyRate != null || perLessonRate != null
    }

    /**
     * Helper to determine which rate type this student uses.
     * Useful for UI display logic.
     */
    fun getRateType(): RateType {
        return when {
            hourlyRate != null -> RateType.HOURLY
            perLessonRate != null -> RateType.PER_LESSON
            else -> RateType.NONE
        }
    }

    /**
     * Formats the rate for display with currency symbol.
     * Returns a user-friendly string like "€25.50/hour" or "€30.00/lesson"
     */
    fun getFormattedRate(): String {
        return when (getRateType()) {
            RateType.HOURLY -> "€%.2f/hour".format(hourlyRate)
            RateType.PER_LESSON -> "€%.2f/lesson".format(perLessonRate)
            RateType.NONE -> "No rate set"
        }
    }
}

/**
 * Enum to represent the different types of rates a student can have.
 * This makes our code more readable and type-safe.
 */
enum class RateType {
    HOURLY,      // Student is charged based on lesson duration
    PER_LESSON,  // Student is charged a flat rate per lesson
    NONE         // No rate set (invalid state)
}