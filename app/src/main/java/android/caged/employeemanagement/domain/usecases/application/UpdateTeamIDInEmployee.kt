package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class UpdateTeamIDInEmployee(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(
        employeeId: Long,
        teamId: Long
    ) {
        employeeRepository.updateEmployeeTeam(employeeId, teamId)
    }
}
