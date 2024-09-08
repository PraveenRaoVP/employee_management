package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.TeamRepository

class GetAllTeamsAsMap(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(): Map<Long, Team> {
        return teamRepository.returnAllTeamsAsMap()
    }
}