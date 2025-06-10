// AppUtils.kt - Fixed currency formatting
package gr.tsambala.tutorbilling.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Formats a Double value as currency with Euro symbol
 * @param amount The amount to format
 * @return Formatted currency string (e.g., "€ 12.34")
 */
fun formatAsCurrency(amount: Double): String {
    return try {
        // Use NumberFormat for proper currency formatting
        val format = NumberFormat.getCurrencyInstance(Locale("el", "GR"))
        format.currency = java.util.Currency.getInstance("EUR")
        format.format(amount)
    } catch (e: Exception) {
        // Fallback to simple formatting if NumberFormat fails
        "€ %.2f".format(Locale.US, amount)
    }
}

/**
 * Alternative: Simple Euro formatting without NumberFormat
 */
fun formatAsEuro(amount: Double): String {
    return "€ %.2f".format(Locale.US, amount)
}

/**
 * Formats minutes as hours and minutes string
 * @param minutes Total minutes
 * @return Formatted string (e.g., "2h 30m")
 */
fun formatDuration(minutes: Int): String {
    val hours = minutes / 60
    val mins = minutes % 60
    return when {
        hours > 0 && mins > 0 -> "${hours}h ${mins}m"
        hours > 0 -> "${hours}h"
        else -> "${mins}m"
    }
}