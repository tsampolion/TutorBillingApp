package gr.tsambala.tutorbilling.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Lesson entity represents a single tutoring session in the database.
 *
 * Think of this as an entry in a teacher's logbook - it records when
 * the lesson happened, how long it lasted, and any notes about the session.
 *
 * The @ForeignKey annotation creates a relationship with the Student table,
 * ensuring that every lesson belongs to a valid student. If a student is
 * deleted, their lessons can be automatically handled based on our rules.
 */
@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            // When a student is deleted, we'll handle it in the ViewModel
            // rather than cascading the delete automatically
            onDelete = ForeignKey.RESTRICT
        )
    ],
    // Index on studentId for faster queries when loading a student's lessons
    indices = [Index("studentId"), Index("date")]
)
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Links this lesson to a specific student
    val studentId: Long,

    // The date of the lesson (without time component)
    // Stored as LocalDate for easy date-based queries and sorting
    val date: LocalDate,

    // When the lesson started
    val startTime: LocalTime,

    // How long the lesson lasted in minutes
    // Using Int for simplicity - partial minutes are rounded
    val durationMinutes: Int,

    // Optional notes about the lesson
    // Teachers might record topics covered, homework assigned, etc.
    val notes: String? = null,

    // Tracks when this record was created and last modified
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
) {
    /**
     * Calculates the end time of the lesson based on start time and duration.
     * Useful for display and scheduling conflict detection.
     */
    fun getEndTime(): LocalTime {
        return startTime.plusMinutes(durationMinutes.toLong())
    }

    /**
     * Converts duration to hours as a decimal for rate calculations.
     * For example, 90 minutes becomes 1.5 hours.
     */
    fun getDurationHours(): Double {
        return durationMinutes / 60.0
    }

    /**
     * Formats the duration for user-friendly display.
     * Shows hours and minutes like "1h 30m" or just "45m" for shorter lessons.
     */
    fun getFormattedDuration(): String {
        val hours = durationMinutes / 60
        val minutes = durationMinutes % 60

        return when {
            hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
            hours > 0 -> "${hours}h"
            else -> "${minutes}m"
        }
    }

    /**
     * Combines date and start time into a single timestamp.
     * Useful for sorting lessons chronologically.
     */
    fun getDateTime(): LocalDateTime {
        return LocalDateTime.of(date, startTime)
    }

    /**
     * Calculates the fee for this lesson based on the student's rate.
     * This is a helper method - the actual calculation will be done
     * in the repository or ViewModel where we have access to the student data.
     *
     * @param student The student this lesson belongs to
     * @return The calculated fee, or 0.0 if no valid rate
     */
    fun calculateFee(student: Student): Double {
        return when (student.getRateType()) {
            RateType.HOURLY -> (student.hourlyRate ?: 0.0) * getDurationHours()
            RateType.PER_LESSON -> student.perLessonRate ?: 0.0
            RateType.NONE -> 0.0
        }
    }

    /**
     * Checks if this lesson is in the current week.
     * Used for calculating weekly totals on the home screen.
     */
    fun isInCurrentWeek(): Boolean {
        val now = LocalDate.now()
        val startOfWeek = now.with(java.time.DayOfWeek.MONDAY)
        val endOfWeek = now.with(java.time.DayOfWeek.SUNDAY)

        return date >= startOfWeek && date <= endOfWeek
    }

    /**
     * Checks if this lesson is in the current month.
     * Used for calculating monthly totals on the home screen.
     */
    fun isInCurrentMonth(): Boolean {
        val now = LocalDate.now()
        return date.year == now.year && date.month == now.month
    }
}