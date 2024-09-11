package android.caged.employeemanagement.domain.repository

import android.caged.employeemanagement.domain.model.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getEmployeeById(employeeId: Long) : Flow<Employee?>

    suspend fun insertEmployee(employee: Employee): Long

    fun getEmployeesByTeamName(teamName: String) : Flow<List<Employee>>

    fun searchEmployeesByName(searchQuery: String) : Flow<List<Employee>>

    fun getAllEmployees() : Flow<List<Employee>>

    suspend fun deleteEmployeeById(employeeId: Long)

    suspend fun updateProfileImageUrl(employeeId: Long, profileImageUrl: String)

    suspend fun getEmployeeCount() : Int

    fun getRecentEmployees() : Flow<List<Employee>>

    fun getRecentEmployeesByTeamId(teamId: Long) : Flow<List<Employee>>

    fun searchEmployee(searchQuery: String) : Flow<List<Employee>>

    fun searchEmployeeByTeam(searchQuery: String, teamID: Long) : Flow<List<Employee>>

    suspend fun getEmployeeCountByTeamID(teamId: Long): Int
    suspend fun updateEmployeeTeam(employeeId: Long, teamID: Long)
    fun getEmployeesByTeamId(teamID: Long): Flow<List<Employee>>
}