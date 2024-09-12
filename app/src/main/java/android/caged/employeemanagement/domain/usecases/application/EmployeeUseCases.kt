package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.repository.EmployeeRepository

class EmployeeUseCases(
    private val employeeRepository: EmployeeRepository
) {
    fun getEmployeeById(employeeId: Long) = employeeRepository.getEmployeeById(employeeId)
    suspend fun insertEmployee(employee: Employee) : Long = employeeRepository.insertEmployee(employee)
    fun getAllEmployees() = employeeRepository.getAllEmployees()
    suspend fun deleteEmployeeByID(employeeId: Long) {
        employeeRepository.deleteEmployeeById(employeeId)
    }
    fun getRecentEmployees() = employeeRepository.getRecentEmployees()
    suspend fun updateProfileImage(employeeId: Long, profileImage: String) {
        employeeRepository.updateProfileImageUrl(employeeId, profileImage)
    }
    fun searchEmployee(query: String) = employeeRepository.searchEmployee(query)
    fun searchEmployeeByTeam(query: String, teamId: Long) = employeeRepository.searchEmployeeByTeam(query, teamId)
    fun getEmployeesByTeamID(teamId: Long) = employeeRepository.getEmployeesByTeamId(teamId)
    suspend fun getEmployeeCount() = employeeRepository.getEmployeeCount()
    suspend fun getEmployeeCountByTeam(teamId: Long) = employeeRepository.getEmployeeCountByTeamID(teamId)
    fun findEmployeeById(employeeId: Long) = employeeRepository.getEmployeeById(employeeId)
    fun getRecentEmployeesByTeamId(teamId: Long) = employeeRepository.getRecentEmployeesByTeamId(teamId)
}
