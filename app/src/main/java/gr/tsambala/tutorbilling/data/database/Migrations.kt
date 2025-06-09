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
        // Add the isActive column only if it doesn't already exist. The
        // PRAGMA table_info query returns all columns of the students table
        // so we can safely check for the existence of the column before
        // attempting to add it.

        val columns = database.query("PRAGMA table_info(students)").use { cursor ->
            val index = cursor.getColumnIndex("name")
            generateSequence {
                if (index == -1 || !cursor.moveToNext()) null else cursor.getString(index)
            }.toList()
        }

        if ("isActive" !in columns) {
            try {
                database.execSQL(
                    "ALTER TABLE students ADD COLUMN isActive INTEGER NOT NULL DEFAULT 1"
                )
            } catch (e: SQLiteException) {
                // Ignore duplicate column errors that may occur if the column
                // already exists for some reason.
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
