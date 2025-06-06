package gr.tsambala.tutorbilling.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\u001a\n\u0010\u000f\u001a\u00020\u0003*\u00020\u0010\u001a\n\u0010\u0011\u001a\u00020\u0003*\u00020\u0005\u001a\u001b\u0010\u0012\u001a\u00020\u0003*\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0014\u001a\n\u0010\u0015\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u0016\u001a\u00020\u0003*\u00020\u0017\u001a\n\u0010\u0016\u001a\u00020\u0003*\u00020\u0010\u001a\n\u0010\u0018\u001a\u00020\u0003*\u00020\u0017\u001a\n\u0010\u0019\u001a\u00020\u001a*\u00020\u0017\u001a\n\u0010\u001b\u001a\u00020\u001a*\u00020\u0017\u001a\n\u0010\u001c\u001a\u00020\u001a*\u00020\u0003\u001a\n\u0010\u001d\u001a\u00020\u001a*\u00020\u0003\u001a\n\u0010\u001e\u001a\u00020\u0003*\u00020\u0003\u001a\n\u0010\u001f\u001a\u00020\u0005*\u00020\u0001\u001a\u0014\u0010 \u001a\u00020\u0005*\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u001a\u0014\u0010!\u001a\u00020\u0001*\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0001\u001a\n\u0010\"\u001a\u00020\u0003*\u00020\u0017\u001a\u0012\u0010#\u001a\u00020\u0003*\u00020\u00032\u0006\u0010$\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\fX\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0003X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0003X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"ANIMATION_DURATION_MS", "", "DATE_PATTERN_DISPLAY", "", "DEFAULT_HOURLY_RATE", "", "DEFAULT_LESSON_DURATION_MINUTES", "MAX_LESSON_DURATION_MINUTES", "MAX_LESSON_NOTES_LENGTH", "MAX_STUDENT_NAME_LENGTH", "MIN_LESSON_DURATION_MINUTES", "SEARCH_DEBOUNCE_DELAY_MS", "", "TIME_PATTERN_12H", "TIME_PATTERN_24H", "format12Hour", "Ljava/time/LocalTime;", "formatAsCurrency", "formatAsCurrencyOrDefault", "default", "(Ljava/lang/Double;Ljava/lang/String;)Ljava/lang/String;", "formatAsDuration", "formatForDisplay", "Ljava/time/LocalDate;", "formatShort", "isInCurrentMonth", "", "isInCurrentWeek", "isValidPositiveDouble", "isValidPositiveInt", "titleCase", "toDecimalHours", "toDoubleOrDefault", "toIntOrDefault", "toRelativeString", "truncate", "maxLength", "app_debug"})
public final class AppUtilsKt {
    
    /**
     * App-wide constants for consistency.
     */
    public static final int MAX_STUDENT_NAME_LENGTH = 100;
    public static final int MAX_LESSON_DURATION_MINUTES = 480;
    public static final int MIN_LESSON_DURATION_MINUTES = 5;
    public static final int MAX_LESSON_NOTES_LENGTH = 500;
    public static final long SEARCH_DEBOUNCE_DELAY_MS = 300L;
    public static final int ANIMATION_DURATION_MS = 300;
    public static final int DEFAULT_LESSON_DURATION_MINUTES = 60;
    public static final double DEFAULT_HOURLY_RATE = 25.0;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATE_PATTERN_DISPLAY = "dd MMM yyyy";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TIME_PATTERN_24H = "HH:mm";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TIME_PATTERN_12H = "h:mm a";
    
    /**
     * Formats a Double as currency with the Euro symbol.
     * This ensures all monetary values are displayed consistently.
     *
     * Examples:
     * - 25.5 -> "€25.50"
     * - 100.0 -> "€100.00"
     * - 0.0 -> "€0.00"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatAsCurrency(double $this$formatAsCurrency) {
        return null;
    }
    
    /**
     * Formats a nullable Double as currency, returning a default for null values.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatAsCurrencyOrDefault(@org.jetbrains.annotations.Nullable()
    java.lang.Double $this$formatAsCurrencyOrDefault, @org.jetbrains.annotations.NotNull()
    java.lang.String p1_772401952) {
        return null;
    }
    
    /**
     * Formats a LocalDate for display using localized formatting.
     * This respects the user's locale settings for date display.
     *
     * Examples (in US locale):
     * - 2024-03-15 -> "Mar 15, 2024"
     * - 2024-12-25 -> "Dec 25, 2024"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatForDisplay(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate $this$formatForDisplay) {
        return null;
    }
    
    /**
     * Formats a LocalDate in a short format for compact display.
     * Useful in lists or cards where space is limited.
     *
     * Examples:
     * - 2024-03-15 -> "15/03"
     * - 2024-12-25 -> "25/12"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatShort(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate $this$formatShort) {
        return null;
    }
    
    /**
     * Checks if this date is in the current week.
     * Uses the system's locale to determine week boundaries.
     */
    public static final boolean isInCurrentWeek(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate $this$isInCurrentWeek) {
        return false;
    }
    
    /**
     * Checks if this date is in the current month.
     */
    public static final boolean isInCurrentMonth(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate $this$isInCurrentMonth) {
        return false;
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
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String toRelativeString(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate $this$toRelativeString) {
        return null;
    }
    
    /**
     * Formats a LocalTime for display in 24-hour format.
     *
     * Examples:
     * - 09:30 -> "09:30"
     * - 14:45 -> "14:45"
     * - 00:00 -> "00:00"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatForDisplay(@org.jetbrains.annotations.NotNull()
    java.time.LocalTime $this$formatForDisplay) {
        return null;
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
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String format12Hour(@org.jetbrains.annotations.NotNull()
    java.time.LocalTime $this$format12Hour) {
        return null;
    }
    
    /**
     * Formats minutes as a human-readable duration.
     *
     * Examples:
     * - 30 -> "30m"
     * - 60 -> "1h"
     * - 90 -> "1h 30m"
     * - 150 -> "2h 30m"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatAsDuration(int $this$formatAsDuration) {
        return null;
    }
    
    /**
     * Converts minutes to decimal hours for calculations.
     *
     * Examples:
     * - 30 -> 0.5
     * - 60 -> 1.0
     * - 90 -> 1.5
     */
    public static final double toDecimalHours(int $this$toDecimalHours) {
        return 0.0;
    }
    
    /**
     * Validates if a string can be parsed as a positive double.
     * Useful for validating rate inputs.
     */
    public static final boolean isValidPositiveDouble(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$isValidPositiveDouble) {
        return false;
    }
    
    /**
     * Validates if a string can be parsed as a positive integer.
     * Useful for validating duration inputs.
     */
    public static final boolean isValidPositiveInt(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$isValidPositiveInt) {
        return false;
    }
    
    /**
     * Safely converts a string to Double with a default value.
     */
    public static final double toDoubleOrDefault(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$toDoubleOrDefault, double p1_772401952) {
        return 0.0;
    }
    
    /**
     * Safely converts a string to Int with a default value.
     */
    public static final int toIntOrDefault(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$toIntOrDefault, int p1_772401952) {
        return 0;
    }
    
    /**
     * Truncates a string to a maximum length with ellipsis.
     * Useful for displaying long text in limited space.
     *
     * Examples:
     * - "Short text".truncate(20) -> "Short text"
     * - "This is a very long text".truncate(10) -> "This is a..."
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String truncate(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$truncate, int maxLength) {
        return null;
    }
    
    /**
     * Capitalizes the first letter of each word.
     * Useful for formatting names.
     *
     * Example:
     * - "john smith" -> "John Smith"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String titleCase(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$titleCase) {
        return null;
    }
}