package gr.tsambala.tutorbilling.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * AppUtils contains utility functions and extension methods used throughout the app.
 *
 * These are like the tools in your toolbox - small, reusable pieces of functionality
 * that make the rest of your code cleaner and more consistent. By centralizing
 * these utilities, we ensure consistent formatting and behavior across the app.
 */

// ===== Currency Formatting =====

/**
 * Formats a Double as currency with the Euro symbol.
 * This ensures all monetary values are displayed consistently.
 *
 * Examples:
 * - 25.5 -> "€25.50"
 * - 100.0 -> "€100.00"
 * - 0.0 -> "€0.00"
 */
fun Double.formatAsCurrency(symbol: String = "€", decimals: Int = 2): String {
    return "$symbol%.${'$'}decimalsf".format(this)
}

/**
 * Formats a nullable Double as currency, returning a default for null values.
 */
fun Double?.formatAsCurrencyOrDefault(symbol: String = "€", decimals: Int = 2): String {
    val default = "$symbol%.${'$'}decimalsf".format(0.0)
    return this?.formatAsCurrency(symbol, decimals) ?: default
}

// ===== Date Formatting =====

/**
 * Formats a LocalDate for display using localized formatting.
 * This respects the user's locale settings for date display.
 *
 * Examples (in US locale):
 * - 2024-03-15 -> "Mar 15, 2024"
 * - 2024-12-25 -> "Dec 25, 2024"
 */
fun LocalDate.formatForDisplay(): String {
    return format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
}

/**
 * Formats a LocalDate in a short format for compact display.
 * Useful in lists or cards where space is limited.
 *
 * Examples:
 * - 2024-03-15 -> "15/03"
 * - 2024-12-25 -> "25/12"
 */
fun LocalDate.formatShort(): String {
    return format(DateTimeFormatter.ofPattern("dd/MM"))
}

/**
 * Checks if this date is in the current week.
 * Uses the system's locale to determine week boundaries.
 */
fun LocalDate.isInCurrentWeek(): Boolean {
    val now = LocalDate.now()
    val weekFields = WeekFields.of(Locale.getDefault())
    return this.get(weekFields.weekOfWeekBasedYear()) == now.get(weekFields.weekOfWeekBasedYear()) &&
            this.year == now.year
}

/**
 * Checks if this date is in the current month.
 */
fun LocalDate.isInCurrentMonth(): Boolean {
    val now = LocalDate.now()
    return this.month == now.month && this.year == now.year
}

/**
 * Gets a human-readable relative date string.
 *
 * Examples:
 * - Today -> "Today"
 * - Yesterday -> "Yesterday"
 * - Within 7 days -> "Monday" (or appropriate day name)
 * - Otherwise -> "Mar 15" (formatted date)
 */
fun LocalDate.toRelativeString(): String {
    val today = LocalDate.now()
    return when (this) {
        today -> "Today"
        today.minusDays(1) -> "Yesterday"
        today.plusDays(1) -> "Tomorrow"
        in today.minusDays(7)..today.plusDays(7) -> {
            format(DateTimeFormatter.ofPattern("EEEE"))
        }
        else -> formatForDisplay()
    }
}

// ===== Time Formatting =====

/**
 * Formats a LocalTime for display in 24-hour format.
 *
 * Examples:
 * - 09:30 -> "09:30"
 * - 14:45 -> "14:45"
 * - 00:00 -> "00:00"
 */
fun LocalTime.formatForDisplay(): String {
    return format(DateTimeFormatter.ofPattern("HH:mm"))
}

/**
 * Formats a LocalTime in 12-hour format with AM/PM.
 * Some users prefer this format.
 *
 * Examples:
 * - 09:30 -> "9:30 AM"
 * - 14:45 -> "2:45 PM"
 * - 00:00 -> "12:00 AM"
 */
fun LocalTime.format12Hour(): String {
    return format(DateTimeFormatter.ofPattern("h:mm a"))
}

// ===== Duration Formatting =====

/**
 * Formats minutes as a human-readable duration.
 *
 * Examples:
 * - 30 -> "30m"
 * - 60 -> "1h"
 * - 90 -> "1h 30m"
 * - 150 -> "2h 30m"
 */
fun Int.formatAsDuration(): String {
    val hours = this / 60
    val minutes = this % 60

    return when {
        hours == 0 -> "${minutes}m"
        minutes == 0 -> "${hours}h"
        else -> "${hours}h ${minutes}m"
    }
}

/**
 * Converts minutes to decimal hours for calculations.
 *
 * Examples:
 * - 30 -> 0.5
 * - 60 -> 1.0
 * - 90 -> 1.5
 */
fun Int.toDecimalHours(): Double {
    return this / 60.0
}

// ===== Validation Extensions =====

/**
 * Validates if a string can be parsed as a positive double.
 * Useful for validating rate inputs.
 */
fun String.isValidPositiveDouble(): Boolean {
    return toDoubleOrNull()?.let { it > 0 } ?: false
}

/**
 * Validates if a string can be parsed as a positive integer.
 * Useful for validating duration inputs.
 */
fun String.isValidPositiveInt(): Boolean {
    return toIntOrNull()?.let { it > 0 } ?: false
}

/**
 * Safely converts a string to Double with a default value.
 */
fun String.toDoubleOrDefault(default: Double = 0.0): Double {
    return toDoubleOrNull() ?: default
}

/**
 * Safely converts a string to Int with a default value.
 */
fun String.toIntOrDefault(default: Int = 0): Int {
    return toIntOrNull() ?: default
}

// ===== String Extensions =====

/**
 * Truncates a string to a maximum length with ellipsis.
 * Useful for displaying long text in limited space.
 *
 * Examples:
 * - "Short text".truncate(20) -> "Short text"
 * - "This is a very long text".truncate(10) -> "This is a..."
 */
fun String.truncate(maxLength: Int): String {
    return if (length <= maxLength) this else "${take(maxLength - 3)}..."
}

/**
 * Capitalizes the first letter of each word.
 * Useful for formatting names.
 *
 * Example:
 * - "john smith" -> "John Smith"
 */
fun String.titleCase(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}

// ===== Constants =====

/**
 * App-wide constants for consistency.
 */
    // Validation limits
    const val MAX_STUDENT_NAME_LENGTH = 100
    const val MAX_LESSON_DURATION_MINUTES = 480 // 8 hours
    const val MIN_LESSON_DURATION_MINUTES = 5
    const val MAX_LESSON_NOTES_LENGTH = 500

    // UI Constants
    const val SEARCH_DEBOUNCE_DELAY_MS = 300L
    const val ANIMATION_DURATION_MS = 300

    // Default values
    const val DEFAULT_LESSON_DURATION_MINUTES = 60
    const val DEFAULT_HOURLY_RATE = 25.0

    // Date/Time patterns
    const val DATE_PATTERN_DISPLAY = "dd MMM yyyy"
    const val TIME_PATTERN_24H = "HH:mm"
    const val TIME_PATTERN_12H = "h:mm a"
