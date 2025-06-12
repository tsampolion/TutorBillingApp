package gr.tsambala.tutorbilling.data.database

import android.annotation.SuppressLint
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Manual migration helpers used in unit tests.
 */

// Adds an isPaid flag to lessons.
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE lessons ADD COLUMN isPaid INTEGER NOT NULL DEFAULT 0")
    }
}

// Adds an isActive flag to students if it does not already exist.
val MIGRATION_2_3 = object : Migration(2, 3) {
    @SuppressLint("Range")
    override fun migrate(db: SupportSQLiteDatabase) {
        val cursor = db.query("PRAGMA table_info(students)")
        var found = false
        val nameIndex = cursor.getColumnIndex("name")
        while (cursor.moveToNext()) {
            if (nameIndex != -1 && cursor.getString(nameIndex) == "isActive") {
                found = true
                break
            }
        }
        cursor.close()
        if (!found) {
            db.execSQL("ALTER TABLE students ADD COLUMN isActive INTEGER NOT NULL DEFAULT 1")
        }
    }
}

// Ensures indices exist and reuses MIGRATION_2_3 so it can run repeatedly.
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        MIGRATION_2_3.migrate(db)
        db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_date ON lessons(date)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_studentId ON lessons(studentId)")
    }
}

// Placeholder migration.
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) { /* no-op */ }
}

// Rebuilds the lessons table to match the Room entities.
val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE lessons_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                studentId INTEGER NOT NULL,
                date TEXT NOT NULL,
                startTime TEXT NOT NULL,
                durationMinutes INTEGER NOT NULL,
                notes TEXT,
                isPaid INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(studentId) REFERENCES students(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            INSERT INTO lessons_new (id, studentId, date, startTime, durationMinutes, notes, isPaid)
            SELECT id, studentId, date, startTime, durationMinutes, notes, isPaid FROM lessons
            """.trimIndent()
        )
        db.execSQL("DROP TABLE lessons")
        db.execSQL("ALTER TABLE lessons_new RENAME TO lessons")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_date ON lessons(date)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_studentId ON lessons(studentId)")
    }
}

// Destructive fallback used only in tests.
val MIGRATION_FALLBACK = object : Migration(1, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS lessons")
        db.execSQL("DROP TABLE IF EXISTS students")
        db.execSQL(
            """
            CREATE TABLE students (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                rate REAL NOT NULL,
                isActive INTEGER NOT NULL DEFAULT 1
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE lessons (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                studentId INTEGER NOT NULL,
                date TEXT NOT NULL,
                startTime TEXT NOT NULL,
                durationMinutes INTEGER NOT NULL,
                notes TEXT,
                isPaid INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(studentId) REFERENCES students(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL("CREATE INDEX index_lessons_date ON lessons(date)")
        db.execSQL("CREATE INDEX index_lessons_studentId ON lessons(studentId)")
    }
}
