package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.TeamRepository

class DeleteTeam(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(teamId: Long) {
        teamRepository.deleteTeamById(teamId)
    }
}