package android.caged.employeemanagement.domain.repository

import android.caged.employeemanagement.domain.model.Employee

interface EmployeeRepository {
    suspend fun getEmployeeById(employeeId: Long) : Employee?

    suspend fun insertEmployee(employee: Employee): Long

    suspend fun getEmployeesByTeamName(teamName: String) : List<Employee>

    suspend fun searchEmployeesByName(searchQuery: String) : List<Employee>

    suspend fun getAllEmployees() : List<Employee>

    suspend fun deleteEmployeeById(employeeId: Long)

    suspend fun updateProfileImageUrl(employeeId: Long, profileImageUrl: String)

    suspend fun getEmployeeCount() : Int

    suspend fun getRecentEmployees() : List<Employee>

    suspend fun getRecentEmployeesByTeamId(teamId: Long) : List<Employee>

    suspend fun searchEmployee(searchQuery: String) : List<Employee>

    suspend fun searchEmployeeByTeam(searchQuery: String, teamID: Long) : List<Employee>

    suspend fun getEmployeeCountByTeamID(teamId: Long): Int
    suspend fun updateEmployeeTeam(employeeId: Long, teamID: Long)
    suspend fun getEmployeesByTeamId(teamID: Long): List<Employee>
}