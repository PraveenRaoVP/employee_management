package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class GetEmployeeCount(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(): Int {
        return employeeRepository.getEmployeeCount()
    }
}

class GetEmployeeCountByTeam(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(teamId: Long): Int {
        return employeeRepository.getEmployeeCountByTeamID(teamId)
    }
}