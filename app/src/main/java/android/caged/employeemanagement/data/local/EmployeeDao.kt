package android.caged.employeemanagement.data.local

import android.caged.employeemanagement.domain.model.Employee
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee) : Long

    @Query("SELECT * FROM employee WHERE employeeId = :employeeId LIMIT 1")
    suspend fun getEmployeeById(employeeId: Long): Employee?

    @Query("SELECT * FROM employee WHERE teamID = (SELECT teamID FROM team WHERE teamName = :teamName)")
    suspend fun getEmployeesByTeamName(teamName: String): List<Employee>

    @Query("SELECT * FROM employee WHERE teamID = :teamID")
    suspend fun getEmployeesByTeamName(teamID: Long): List<Employee>

    @Query("SELECT * FROM employee WHERE employeeName LIKE '%' || :searchQuery || '%'")
    suspend fun searchEmployeesByName(searchQuery: String): List<Employee>

    @Query("UPDATE employee SET profileImageUrl = :profileImageUrl WHERE employeeId = :employeeId")
    suspend fun updateProfileImageUrl(employeeId: Long, profileImageUrl: String)

    @Query("SELECT * FROM employee")
    suspend fun getAllEmployees(): List<Employee>

    @Query("DELETE FROM employee WHERE employeeId = :employeeId")
    suspend fun deleteEmployeeById(employeeId: Long)

    @Query("SELECT COUNT(*) FROM employee")
    suspend fun getEmployeeCount(): Int

    // get employee count as per team id
    @Query("SELECT COUNT(*) FROM employee WHERE teamID = :teamId")
    suspend fun getEmployeeCountByTeamID(teamId: Long): Int

    @Query("SELECT * FROM employee ORDER BY employeeId DESC LIMIT 5")
    suspend fun getRecentEmployees(): List<Employee>

    @Query("SELECT * FROM employee WHERE teamID = :teamId ORDER BY employeeId DESC LIMIT 5")
    suspend fun getRecentEmployeesByTeamId(teamId: Long): List<Employee>

//    @Query("SELECT * FROM employee WHERE employeeName LIKE :searchQuery")
//    suspend fun searchEmployee(searchQuery: String) : List<Employee>
//
    @Query("SELECT * FROM employee WHERE employeeName LIKE '%' || :searchQuery || '%' AND teamID = :teamID")
    suspend fun searchEmployeeByTeam(searchQuery: String, teamID: Long) : List<Employee>

    @Query("UPDATE employee SET teamID = :teamID WHERE employeeId = :employeeId")
    suspend fun updateEmployeeTeam(employeeId: Long, teamID: Long)
}