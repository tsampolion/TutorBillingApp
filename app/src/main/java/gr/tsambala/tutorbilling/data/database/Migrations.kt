package gr.tsambala.tutorbilling.data.database

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
        // Add the isActive column only if it doesn't already exist
        val cursor = database.query("PRAGMA table_info(students)")
        var hasColumn = false
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndexOrThrow("name")) == "isActive") {
                hasColumn = true
                break
            }
        }
        cursor.close()
        if (!hasColumn) {
            database.execSQL("ALTER TABLE students ADD COLUMN isActive INTEGER NOT NULL DEFAULT 1")
        }
    }
}
