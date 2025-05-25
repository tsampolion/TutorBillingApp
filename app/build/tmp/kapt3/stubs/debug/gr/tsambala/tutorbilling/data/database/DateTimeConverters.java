package gr.tsambala.tutorbilling.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.Student;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TypeConverters tell Room how to store custom data types in the database.
 *
 * SQLite (the underlying database) only understands basic types like
 * numbers and text. These converters are like translators that convert
 * our Java time objects into numbers for storage and back again for use.
 *
 * Think of it like packing clothes in a suitcase - we compress (convert)
 * them for storage and unfold (convert back) them when we need to use them.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0011\u001a\u0004\u0018\u00010\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u0004H\u0007\u00a2\u0006\u0002\u0010\u0013J\u0019\u0010\u0014\u001a\u0004\u0018\u00010\n2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0004H\u0007\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\rH\u0007\u00a2\u0006\u0002\u0010\u0019\u00a8\u0006\u001a"}, d2 = {"Lgr/tsambala/tutorbilling/data/database/DateTimeConverters;", "", "()V", "fromInstant", "", "instant", "Ljava/time/Instant;", "(Ljava/time/Instant;)Ljava/lang/Long;", "fromLocalDate", "date", "Ljava/time/LocalDate;", "(Ljava/time/LocalDate;)Ljava/lang/Long;", "fromLocalTime", "", "time", "Ljava/time/LocalTime;", "(Ljava/time/LocalTime;)Ljava/lang/Integer;", "toInstant", "milliseconds", "(Ljava/lang/Long;)Ljava/time/Instant;", "toLocalDate", "epochDay", "(Ljava/lang/Long;)Ljava/time/LocalDate;", "toLocalTime", "secondOfDay", "(Ljava/lang/Integer;)Ljava/time/LocalTime;", "app_debug"})
public final class DateTimeConverters {
    
    public DateTimeConverters() {
        super();
    }
    
    /**
     * Converts Instant to Long (milliseconds since epoch) for storage.
     * This is like converting a full timestamp into a simple number.
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long fromInstant(@org.jetbrains.annotations.Nullable()
    java.time.Instant instant) {
        return null;
    }
    
    /**
     * Converts Long back to Instant when reading from database.
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.Instant toInstant(@org.jetbrains.annotations.Nullable()
    java.lang.Long milliseconds) {
        return null;
    }
    
    /**
     * Converts LocalDate to Long (days since epoch) for storage.
     * We use epoch days to maintain date accuracy without timezone issues.
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long fromLocalDate(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate date) {
        return null;
    }
    
    /**
     * Converts Long back to LocalDate when reading from database.
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate toLocalDate(@org.jetbrains.annotations.Nullable()
    java.lang.Long epochDay) {
        return null;
    }
    
    /**
     * Converts LocalTime to Int (seconds since midnight) for storage.
     * This preserves time accuracy down to the second.
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer fromLocalTime(@org.jetbrains.annotations.Nullable()
    java.time.LocalTime time) {
        return null;
    }
    
    /**
     * Converts Int back to LocalTime when reading from database.
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalTime toLocalTime(@org.jetbrains.annotations.Nullable()
    java.lang.Integer secondOfDay) {
        return null;
    }
}