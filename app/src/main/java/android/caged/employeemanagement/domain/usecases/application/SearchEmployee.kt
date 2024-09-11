package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class SearchEmployee(
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke(query: String) = employeeRepository.searchEmployee(query)
}

class SearchEmployeeByTeam(
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke(query: String, teamId: Long) = employeeRepository.searchEmployeeByTeam(query, teamId)
}