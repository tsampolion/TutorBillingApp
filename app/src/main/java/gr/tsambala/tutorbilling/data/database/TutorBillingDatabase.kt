package gr.tsambala.tutorbilling.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import gr.tsambala.tutorbilling.data.dao.LessonDao
import gr.tsambala.tutorbilling.data.dao.StudentDao
import gr.tsambala.tutorbilling.data.model.Lesson
import gr.tsambala.tutorbilling.data.model.Student
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

/**
 * TutorBillingDatabase is the main database class for the app.
 *
 * Think of this as the blueprint for your entire filing system - it defines
 * what types of records (entities) can be stored and provides access to
 * the librarians (DAOs) who know how to handle those records.
 *
 * @Database annotation tells Room:
 * - Which entities (tables) this database contains
 * - The version number (important for migrations when we change the schema)
 * - Whether to export the schema (useful for version control)
 */
@Database(
    entities = [Student::class, Lesson::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateTimeConverters::class)
abstract class TutorBillingDatabase : RoomDatabase() {

    /**
     * Provides access to student-related database operations.
     * Room will generate the implementation of this abstract method.
     */
    abstract fun studentDao(): StudentDao

    /**
     * Provides access to lesson-related database operations.
     */
    abstract fun lessonDao(): LessonDao

    companion object {
        /**
         * Database name constant.
         * The actual database file will be stored as "tutor_billing.db"
         * in the app's private storage.
         */
        const val DATABASE_NAME = "tutor_billing_database"
    }
}

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