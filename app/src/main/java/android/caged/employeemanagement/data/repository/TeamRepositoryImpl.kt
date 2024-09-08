package android.caged.employeemanagement.data.repository

import android.caged.employeemanagement.data.local.TeamDao
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.TeamRepository

class TeamRepositoryImpl (
    private val teamDao: TeamDao
): TeamRepository {
    override suspend fun insertTeam(team: Team) : Long {
        return teamDao.insertTeam(team)
    }

    override suspend fun getTeamByName(teamName: String): Team? {
        return teamDao.getTeamByName(teamName)
    }

    override suspend fun getTeamById(teamId: Long): Team? {
        return teamDao.getTeamById(teamId)
    }

    override suspend fun getTeamCount(): Int {
        return teamDao.getTeamCount()
    }

    override suspend fun returnAllTeamsAsMap(): Map<Long, Team> {
        val teamList = teamDao.getAllTeams()
        val teamMap = mutableMapOf<Long, Team>()
        for (team in teamList) {
            teamMap[team.teamID] = team
        }
        return teamMap
    }

    override suspend fun deleteTeamById(teamId: Long) {
        teamDao.deleteTeamById(teamId)
    }
}