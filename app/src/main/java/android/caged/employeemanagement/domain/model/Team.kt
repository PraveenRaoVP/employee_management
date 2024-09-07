package android.caged.employeemanagement.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val teamID: Long = 0,
    val teamName: String,
    val teamLeadID: Long
)
