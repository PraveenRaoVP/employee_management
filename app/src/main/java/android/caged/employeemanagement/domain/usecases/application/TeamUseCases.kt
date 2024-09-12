package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.TeamRepository

//data class X(
//    val createTeam: CreateTeam,
//    val getTeamCount: GetTeamCount,
//    val getTeamByID: GetTeamByID,
//    val getTeamByName: GetTeamByName,
//    val deleteTeam: DeleteTeam,
//    val updateTeamLeadIDInTeam: UpdateTeamLeadIDInTeam,
//    val getTeamDetailsById: GetTeamDetailsById,
//    val getAllTeamsAsMap: GetAllTeamsAsMap
//)

class TeamUseCases(
    private val teamRepository: TeamRepository
) {
    suspend fun createTeam(team: Team) : Long {
        return teamRepository.insertTeam(team)
    }

    suspend fun getTeamCount() : Int {
        return teamRepository.getTeamCount()
    }

    suspend fun getTeamByID(teamId: Long) = teamRepository.getTeamById(teamId)

    suspend fun getTeamByName(teamName: String) = teamRepository.getTeamByName(teamName)

    suspend fun deleteTeam(teamId: Long) {
        teamRepository.deleteTeamById(teamId)
    }

    suspend fun updateTeamLeadIDInTeam(teamId: Long, teamLeadId: Long) {
        teamRepository.updateTeamLeadIdInTeam(teamId, teamLeadId)
    }

    suspend fun getTeamDetailsById(teamId: Long) = teamRepository.getTeamById(teamId)

    suspend fun getAllTeamsAsMap() = teamRepository.returnAllTeamsAsMap()
}