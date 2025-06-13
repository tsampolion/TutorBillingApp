package gr.tsambala.tutorbilling.data.database

import android.content.Context
import android.database.Cursor
import org.robolectric.RobolectricTestRunner
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(RobolectricTestRunner::class)
class MigrationsTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @get:Rule
    val helper = MigrationTestHelper(
        instrumentation,
        TutorBillingDatabase::class.java
    )

    @Test
    fun migrateAllVersionsToLatest() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        for (version in 1..7) {
            helper.createDatabase(TEST_DB, version).close()
            helper.runMigrationsAndValidate(TEST_DB, 8, true)
            context.deleteDatabase(TEST_DB)
        }
    }

    @Test
    fun autoMigration5To6_createsLessonsTableProperly() {
        // Create database at version 5 with sample data
        helper.createDatabase(TEST_DB, 5).apply {
            execSQL("INSERT INTO students (id, name, hourlyRate, perLessonRate, createdAt, updatedAt, isActive) VALUES (1, 'Alice', 20.0, NULL, 0, 0, 1)")
            execSQL("INSERT INTO lessons (id, studentId, date, startTime, durationMinutes, notes, isPaid, createdAt, updatedAt) VALUES (1, 1, 1697040000, 1697043600, 60, 'note', 0, 0, 0)")
            close()
        }

        val db = helper.runMigrationsAndValidate(TEST_DB, 6, true)

        // Verify column names of lessons table
        val columns = mutableListOf<String>()
        db.query("PRAGMA table_info(lessons)").use { cursor: Cursor ->
            val nameIndex = cursor.getColumnIndex("name")
            while (cursor.moveToNext()) {
                columns.add(cursor.getString(nameIndex))
            }
        }
        assertEquals(
            listOf(
                "id",
                "studentId",
                "date",
                "startTime",
                "durationMinutes",
                "notes",
                "isPaid"
            ),
            columns
        )

        // Verify row migrated
        db.query("SELECT COUNT(*) FROM lessons").use { cursor: Cursor ->
            cursor.moveToFirst()
            assertEquals(1, cursor.getInt(0))
        }

        // Verify indexes on lessons table
        val indexes = mutableListOf<String>()
        db.query("PRAGMA index_list('lessons')").use { cursor: Cursor ->
            val nameIndex = cursor.getColumnIndex("name")
            while (cursor.moveToNext()) {
                indexes.add(cursor.getString(nameIndex))
            }
        }
        assertTrue(indexes.contains("index_lessons_date"))
        assertTrue(indexes.contains("index_lessons_studentId"))

        // Verify foreign key uses CASCADE delete
        db.query("PRAGMA foreign_key_list('lessons')").use { cursor: Cursor ->
            cursor.moveToFirst()
            val onDeleteIndex = cursor.getColumnIndex("on_delete")
            assertEquals("CASCADE", cursor.getString(onDeleteIndex))
        }
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }
}
