package gr.tsambala.tutorbilling.utils;

import gr.tsambala.tutorbilling.data.model.RateType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * App-wide constants for consistency.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lgr/tsambala/tutorbilling/utils/AppConstants;", "", "()V", "ANIMATION_DURATION_MS", "", "DATE_PATTERN_DISPLAY", "", "DEFAULT_HOURLY_RATE", "", "DEFAULT_LESSON_DURATION_MINUTES", "MAX_LESSON_DURATION_MINUTES", "MAX_LESSON_NOTES_LENGTH", "MAX_STUDENT_NAME_LENGTH", "MIN_LESSON_DURATION_MINUTES", "SEARCH_DEBOUNCE_DELAY_MS", "", "TIME_PATTERN_12H", "TIME_PATTERN_24H", "app_debug"})
public final class AppConstants {
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
    @org.jetbrains.annotations.NotNull()
    public static final gr.tsambala.tutorbilling.utils.AppConstants INSTANCE = null;
    
    private AppConstants() {
        super();
    }
}