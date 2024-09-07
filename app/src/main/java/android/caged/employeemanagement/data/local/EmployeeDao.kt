package android.caged.employeemanagement.data.local

import android.caged.employeemanagement.domain.model.Employee
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Query("SELECT * FROM employee WHERE employeeId = :employeeId LIMIT 1")
    suspend fun getEmployeeById(employeeId: Long): Employee?

    @Query("SELECT * FROM employee WHERE teamID = (SELECT teamID FROM team WHERE teamName = :teamName)")
    suspend fun getEmployeesByTeamName(teamName: String): List<Employee>

    @Query("SELECT * FROM employee WHERE employeeName LIKE '%' || :searchQuery || '%'")
    suspend fun searchEmployeesByName(searchQuery: String): List<Employee>

    @Query("UPDATE employee SET profileImageUrl = :profileImageUrl WHERE employeeId = :employeeId")
    suspend fun updateProfileImageUrl(employeeId: Long, profileImageUrl: String)

    @Query("SELECT * FROM employee")
    suspend fun getAllEmployees(): List<Employee>

    @Query("DELETE FROM employee WHERE employeeId = :employeeId")
    suspend fun deleteEmployeeById(employeeId: Long)
}