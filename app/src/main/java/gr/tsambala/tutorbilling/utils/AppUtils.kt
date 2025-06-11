// AppUtils.kt - Fixed currency formatting
package gr.tsambala.tutorbilling.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
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
 * Extension version of [formatAsCurrency] used across the UI.
 *
 * @param symbol Currency symbol to use (defaults to "€")
 * @param decimals Number of decimal places allowed (clamped to 0..2)
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
