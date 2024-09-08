package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.repository.EmployeeRepository

class GetRecentEmployees(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke() : List<Employee> = employeeRepository.getRecentEmployees()
}

class GetRecentEmployeesByTeamId (
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(teamId: Long) : List<Employee> = employeeRepository.getRecentEmployeesByTeamId(teamId)
}