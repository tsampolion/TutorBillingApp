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
        var hasColumn = false
        database.query("PRAGMA table_info(students)").use { cursor ->
            val nameIndex = cursor.getColumnIndex("name")
            while (cursor.moveToNext()) {
                if (nameIndex != -1 && cursor.getString(nameIndex) == "isActive") {
                    hasColumn = true
                    break
                }
            }
        }

        if (!hasColumn) {
            database.execSQL("ALTER TABLE students ADD COLUMN isActive INTEGER NOT NULL DEFAULT 1")
        }
    }
}
