package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.TeamRepository

class GetTeamDetailsById(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(teamId: Long) = teamRepository.getTeamById(teamId)
}