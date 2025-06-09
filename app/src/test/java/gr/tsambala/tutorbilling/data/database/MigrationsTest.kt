package gr.tsambala.tutorbilling.data.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Test

class MigrationsTest {

    @Test
    fun migration3To4_canRunTwice() {
        val context = ApplicationProvider.getApplicationContext<Context>()
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
}
