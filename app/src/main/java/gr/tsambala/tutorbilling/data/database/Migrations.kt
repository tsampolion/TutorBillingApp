// Migrations.kt - Fixed database migrations
package gr.tsambala.tutorbilling.data.database

import android.annotation.SuppressLint
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Migration from version 1 to 2
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add isPaid column to lessons table with default value 0
        db.execSQL("ALTER TABLE lessons ADD COLUMN isPaid INTEGER NOT NULL DEFAULT 0")
    }
}

// Migration from version 2 to 3
val MIGRATION_2_3 = object : Migration(2, 3) {
    @SuppressLint("Range")
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add isActive column to students table with default value 1
        // First check if column already exists
        val cursor = db.query("PRAGMA table_info(students)")
        var hasIsActive = false
        while (cursor.moveToNext()) {
            val columnName = cursor.getString(cursor.getColumnIndex("name"))
            if (columnName == "isActive") {
                hasIsActive = true
                break
            }
        }
        cursor.close()

        if (!hasIsActive) {
            db.execSQL("ALTER TABLE students ADD COLUMN isActive INTEGER NOT NULL DEFAULT 1")
        }
    }
}

// Migration from version 3 to 4
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // This migration seems to be problematic - let's ensure indices are created

        // Create indices if they don't exist
        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_date ON lessons(date)")
        } catch (e: Exception) {
            // Index might already exist
        }

        try {
            db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_studentId ON lessons(studentId)")
        } catch (e: Exception) {
            // Index might already exist
        }

        // Update any isPaid values that might be 1 to 0 if needed
        // This ensures consistency with the expected default
        // Comment this out if you want to preserve existing paid status
        // db.execSQL("UPDATE lessons SET isPaid = 0 WHERE isPaid = 1")
    }
}

// Migration from version 4 to 5 (if needed for future updates)
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Future migration placeholder
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
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
        """.trimIndent())
        db.execSQL("""
            INSERT INTO lessons_new (id, studentId, date, startTime,
                durationMinutes, notes, isPaid)
            SELECT id, studentId, date, startTime,
                durationMinutes, notes, isPaid FROM lessons
        """.trimIndent())
        db.execSQL("DROP TABLE lessons")
        db.execSQL("ALTER TABLE lessons_new RENAME TO lessons")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_date ON lessons(date)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_lessons_studentId ON lessons(studentId)")
    }
}

// Destructive migration fallback - use only as last resort
val MIGRATION_FALLBACK = object : Migration(1, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // This will recreate all tables - data will be lost!
        // Only use this during development or with proper data backup

        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS lessons")
        db.execSQL("DROP TABLE IF EXISTS students")

        // Recreate tables with current schema
        db.execSQL("""
            CREATE TABLE students (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                rate REAL NOT NULL,
                isActive INTEGER NOT NULL DEFAULT 1
            )
        """)

        db.execSQL("""
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
        """)

        // Create indices
        db.execSQL("CREATE INDEX index_lessons_date ON lessons(date)")
        db.execSQL("CREATE INDEX index_lessons_studentId ON lessons(studentId)")
    }
}