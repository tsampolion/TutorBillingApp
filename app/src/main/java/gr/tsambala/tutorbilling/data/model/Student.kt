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
    @ColumnInfo(defaultValue = "")
    val surname: String,
    @ColumnInfo(defaultValue = "")
    val parentMobile: String,
    val parentEmail: String? = null,
    @ColumnInfo(defaultValue = RateTypes.HOURLY)
    val rateType: String = RateTypes.HOURLY,
    val rate: Double,
    @ColumnInfo(defaultValue = "")
    val className: String,
    @ColumnInfo(defaultValue = "1")
    val isActive: Boolean = true // Default to active
)
