// Student.kt - Fixed data model
package gr.tsambala.tutorbilling.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val rate: Double, // Hourly rate in euros
    @ColumnInfo(defaultValue = "1")
    val isActive: Boolean = true // Default to active
)
