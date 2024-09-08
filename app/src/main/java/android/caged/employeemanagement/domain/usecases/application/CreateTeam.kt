package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.TeamRepository

class CreateTeam(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(
        team: Team
    ) : Long {
        return teamRepository.insertTeam(team)
    }
}