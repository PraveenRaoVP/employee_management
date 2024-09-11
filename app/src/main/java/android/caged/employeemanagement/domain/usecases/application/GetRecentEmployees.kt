package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.repository.EmployeeRepository

class GetRecentEmployees(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke() = employeeRepository.getRecentEmployees()
}

class GetRecentEmployeesByTeamId (
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke(teamId: Long) = employeeRepository.getRecentEmployeesByTeamId(teamId)
}