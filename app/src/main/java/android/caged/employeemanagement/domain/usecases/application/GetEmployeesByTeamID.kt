package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class GetEmployeesByTeamID(
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke(teamID: Long) = employeeRepository.getEmployeesByTeamId(teamID)
}