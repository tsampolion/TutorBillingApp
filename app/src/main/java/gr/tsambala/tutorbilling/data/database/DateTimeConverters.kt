package gr.tsambala.tutorbilling.data.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

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
class DateTimeConverters {

    /**
     * Converts Instant to Long (milliseconds since epoch) for storage.
     * This is like converting a full timestamp into a simple number.
     */
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    /**
     * Converts Long back to Instant when reading from database.
     */
    @TypeConverter
    fun toInstant(milliseconds: Long?): Instant? {
        return milliseconds?.let { Instant.ofEpochMilli(it) }
    }

    /**
     * Converts LocalDate to Long (days since epoch) for storage.
     * We use epoch days to maintain date accuracy without timezone issues.
     */
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    /**
     * Converts Long back to LocalDate when reading from database.
     */
    @TypeConverter
    fun toLocalDate(epochDay: Long?): LocalDate? {
        return epochDay?.let { LocalDate.ofEpochDay(it) }
    }

    /**
     * Converts LocalTime to Int (seconds since midnight) for storage.
     * This preserves time accuracy down to the second.
     */
    @TypeConverter
    fun fromLocalTime(time: LocalTime?): Int? {
        return time?.toSecondOfDay()
    }

    /**
     * Converts Int back to LocalTime when reading from database.
     */
    @TypeConverter
    fun toLocalTime(secondOfDay: Int?): LocalTime? {
        return secondOfDay?.let { LocalTime.ofSecondOfDay(it.toLong()) }
    }
}
