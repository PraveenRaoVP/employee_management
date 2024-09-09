package android.caged.employeemanagement.domain.repository

import android.caged.employeemanagement.domain.model.Team

interface TeamRepository {
    suspend fun insertTeam(team: Team): Long

    suspend fun getTeamByName(teamName: String): Team?

    suspend fun getTeamById(teamId: Long): Team?
    suspend fun getTeamCount(): Int
    suspend fun returnAllTeamsAsMap(): Map<Long, Team>
    suspend fun deleteTeamById(teamId: Long)
    suspend fun updateTeamLeadIdInTeam(teamId: Long, teamLeadId: Long)
}