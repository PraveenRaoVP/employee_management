package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.TeamRepository

class UpdateTeamLeadIDInTeam(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(teamId: Long, teamLeadId: Long) {
        teamRepository.updateTeamLeadIdInTeam(teamId, teamLeadId)
    }
}