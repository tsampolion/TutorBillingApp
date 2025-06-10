package gr.tsambala.tutorbilling.data.database

import android.database.sqlite.SQLiteException
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE students ADD COLUMN className TEXT NOT NULL DEFAULT 'Unassigned'")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE students ADD COLUMN surname TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE students ADD COLUMN parentMobile TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE students ADD COLUMN parentEmail TEXT NOT NULL DEFAULT ''")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Check if the isActive column already exists in the students table
        val cursor = database.query("PRAGMA table_info('students')")
        val nameIndex = cursor.getColumnIndex("name")
        var hasColumn = false
        while (cursor.moveToNext()) {
            if (nameIndex != -1 && cursor.getString(nameIndex) == "isActive") {
                hasColumn = true
                break
            }
        }
        cursor.close()

        if (!hasColumn) {
            try {
                database.execSQL("ALTER TABLE students ADD COLUMN isActive INTEGER NOT NULL DEFAULT 1")
            } catch (e: SQLiteException) {
                // Ignore duplicate column errors that may occur if the column already exists
                if (e.message?.contains("duplicate column", ignoreCase = true) != true) {
                    throw e
                }
            }
        }
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE lessons ADD COLUMN isPaid INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE lessons_new (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "studentId INTEGER NOT NULL," +
                "date TEXT NOT NULL," +
                "startTime TEXT NOT NULL," +
                "durationMinutes INTEGER NOT NULL," +
                "notes TEXT," +
                "isPaid INTEGER NOT NULL DEFAULT 0," +
                "FOREIGN KEY(studentId) REFERENCES students(id) ON DELETE CASCADE)"
        )

        database.execSQL(
            "INSERT INTO lessons_new (id, studentId, date, startTime, durationMinutes, notes, isPaid)" +
                " SELECT id, studentId, date, startTime, durationMinutes, notes, isPaid FROM lessons"
        )

        database.execSQL("DROP TABLE lessons")
        database.execSQL("ALTER TABLE lessons_new RENAME TO lessons")
    }
}
