package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.TeamRepository

class GetTeamByName(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(teamName: String) = teamRepository.getTeamByName(teamName)
}