package android.caged.employeemanagement.data.local

import android.caged.employeemanagement.domain.model.Credentials
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Team
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Credentials::class, Employee::class, Team::class], version = 2, exportSchema = false)
@TypeConverters(PositionTypeConverter::class)
abstract class EmployeeManagementDatabase : RoomDatabase() {
    abstract fun credentialsDao(): CredentialsDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun teamDao(): TeamDao
}
