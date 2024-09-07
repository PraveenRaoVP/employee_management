package android.caged.employeemanagement.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val employeeId : Long = 0,
    val employeeName : String,
    val position : Position,
    val designation: String,
    val salary : Double,
    val email : String,
    val teamID : Long,
    val phone : String,
    val profileImageUrl : String
)