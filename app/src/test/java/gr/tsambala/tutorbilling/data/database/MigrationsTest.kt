package gr.tsambala.tutorbilling.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MigrationsTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val TEST_DB = "test-db"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TutorBillingDatabase::class.java,
        FrameworkSQLiteOpenHelperFactory()
    )


    @Test
    fun migration3To4_canRunTwice() {
        val factory = FrameworkSQLiteOpenHelperFactory()
        val configuration = SupportSQLiteOpenHelper.Configuration.builder(context)
            .name(null) // in-memory
            .callback(object : SupportSQLiteOpenHelper.Callback(3) {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL("CREATE TABLE students (id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL)")
                }

                override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {}
            })
            .build()

        val openHelper = factory.create(configuration)
        val db = openHelper.writableDatabase

        // First migration
        MIGRATION_3_4.migrate(db)
        // Second migration should be no-op and not crash
        MIGRATION_3_4.migrate(db)

        // Verify column exists
        val cursor = db.query("PRAGMA table_info(students)")
        var hasColumn = false
        val nameIndex = cursor.getColumnIndex("name")
        while (cursor.moveToNext()) {
            if (nameIndex != -1 && cursor.getString(nameIndex) == "isActive") {
                hasColumn = true
                break
            }
        }
        cursor.close()
        assertTrue(hasColumn)

        db.close()
    }

    @Test
    fun migration5To6_rebuildsLessons() {
        val factory = FrameworkSQLiteOpenHelperFactory()
        val configuration = SupportSQLiteOpenHelper.Configuration.builder(context)
            .name(null)
            .callback(object : SupportSQLiteOpenHelper.Callback(5) {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        "CREATE TABLE students (id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL)"
                    )
                    db.execSQL(
                        "CREATE TABLE lessons (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "studentId INTEGER NOT NULL," +
                            "date TEXT NOT NULL," +
                            "startTime TEXT NOT NULL," +
                            "durationMinutes INTEGER NOT NULL," +
                            "notes TEXT," +
                            "isPaid INTEGER NOT NULL DEFAULT 0," +
                            "FOREIGN KEY(studentId) REFERENCES students(id) ON DELETE CASCADE)"
                    )
                    db.execSQL("INSERT INTO students (id, name) VALUES (1, 'Bob')")
                    db.execSQL(
                        "INSERT INTO lessons (studentId, date, startTime, durationMinutes, notes, isPaid)" +
                            " VALUES (1, '2025-06-01', '10:00', 60, 'note', 0)"
                    )
                }

                override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {}
            })
            .build()

        val openHelper = factory.create(configuration)
        val db = openHelper.writableDatabase

        MIGRATION_5_6.migrate(db)

        val cursor = db.query("PRAGMA table_info(lessons)")
        var defaultValue: String? = null
        val nameIndex = cursor.getColumnIndex("name")
        val defaultIndex = cursor.getColumnIndex("dflt_value")
        while (cursor.moveToNext()) {
            if (nameIndex != -1 && cursor.getString(nameIndex) == "isPaid") {
                defaultValue = cursor.getString(defaultIndex)
                break
            }
        }
        cursor.close()

        val count = db.query("SELECT COUNT(*) FROM lessons").use {
            it.moveToFirst()
            it.getInt(0)
        }

        assertTrue(defaultValue?.contains("0") == true && count == 1)

        db.close()
    }

    @Test
    fun migration1To2_schemaValid() {
        helper.createDatabase(TEST_DB, 1).close()
        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
        val count = db.query("SELECT count(*) FROM room_master_table").use {
            it.moveToFirst()
            it.getInt(0)
        }
        assertTrue(count > 0)
        db.close()
    }

    @Test
    fun migration2To3_schemaValid() {
        helper.createDatabase(TEST_DB, 2).close()
        val db = helper.runMigrationsAndValidate(TEST_DB, 3, true, MIGRATION_2_3)
        val count = db.query("SELECT count(*) FROM room_master_table").use {
            it.moveToFirst()
            it.getInt(0)
        }
        assertTrue(count > 0)
        db.close()
    }

    @Test
    fun migration3To4_schemaValid() {
        helper.createDatabase(TEST_DB, 3).close()
        val db = helper.runMigrationsAndValidate(TEST_DB, 4, true, MIGRATION_3_4)
        val count = db.query("SELECT count(*) FROM room_master_table").use {
            it.moveToFirst()
            it.getInt(0)
        }
        assertTrue(count > 0)
        db.close()
    }

    @Test
    fun migration4To5_schemaValid() {
        helper.createDatabase(TEST_DB, 4).close()
        val db = helper.runMigrationsAndValidate(TEST_DB, 5, true, MIGRATION_4_5)
        val count = db.query("SELECT count(*) FROM room_master_table").use {
            it.moveToFirst()
            it.getInt(0)
        }
        assertTrue(count > 0)
        db.close()
    }

    @Test
    fun migration5To6_schemaValid() {
        helper.createDatabase(TEST_DB, 5).close()
        val db = helper.runMigrationsAndValidate(TEST_DB, 6, true, MIGRATION_5_6)
        val count = db.query("SELECT count(*) FROM room_master_table").use {
            it.moveToFirst()
            it.getInt(0)
        }
        assertTrue(count > 0)
        db.close()
    }

    @Test
    fun migration6To7_schemaValid() {
        helper.createDatabase(TEST_DB, 6).close()
        val db = helper.runMigrationsAndValidate(TEST_DB, 7, true)
        val count = db.query("SELECT count(*) FROM room_master_table").use {
            it.moveToFirst()
            it.getInt(0)
        }
        assertTrue(count > 0)
        db.close()
    }
}
