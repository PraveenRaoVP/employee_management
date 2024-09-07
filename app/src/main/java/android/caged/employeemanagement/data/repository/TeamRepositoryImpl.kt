package android.caged.employeemanagement.data.repository

import android.caged.employeemanagement.data.local.TeamDao
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.TeamRepository

class TeamRepositoryImpl (
    private val teamDao: TeamDao
): TeamRepository {
    override suspend fun insertTeam(team: Team) {
        teamDao.insertTeam(team)
    }

    override suspend fun getTeamByName(teamName: String): Team? {
        return teamDao.getTeamByName(teamName)
    }

    override suspend fun getTeamById(teamId: Long): Team? {
        return teamDao.getTeamById(teamId)
    }
}