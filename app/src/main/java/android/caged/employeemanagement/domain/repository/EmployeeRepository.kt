package android.caged.employeemanagement.domain.repository

import android.caged.employeemanagement.domain.model.Employee

interface EmployeeRepository {
    suspend fun getEmployeeById(employeeId: Long) : Employee?

    suspend fun insertEmployee(employee: Employee)

    suspend fun getEmployeesByTeamName(teamName: String) : List<Employee>

    suspend fun searchEmployeesByName(searchQuery: String) : List<Employee>

    suspend fun getAllEmployees() : List<Employee>

    suspend fun deleteEmployeeById(employeeId: Long)

    suspend fun updateProfileImageUrl(employeeId: Long, profileImageUrl: String)
}