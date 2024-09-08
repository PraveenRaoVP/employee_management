package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.TeamRepository

class GetTeamCount(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(): Int = teamRepository.getTeamCount()
}