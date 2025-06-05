package gr.tsambala.tutorbilling.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE students ADD COLUMN className TEXT NOT NULL DEFAULT 'Unassigned'")
    }
}
