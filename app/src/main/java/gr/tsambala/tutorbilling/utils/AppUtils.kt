package gr.tsambala.tutorbilling.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
    val safeDecimals = decimals.coerceIn(0, 2)
    val symbols = DecimalFormatSymbols().apply { currencySymbol = symbol }
    val pattern = "#,##0.${"0".repeat(safeDecimals)}"
    val formatter = DecimalFormat(pattern, symbols).apply {
        minimumFractionDigits = safeDecimals
        maximumFractionDigits = safeDecimals
    }
    return formatter.format(this)
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
