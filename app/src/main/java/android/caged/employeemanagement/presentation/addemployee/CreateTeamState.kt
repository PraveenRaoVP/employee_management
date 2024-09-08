package android.caged.employeemanagement.presentation.addemployee

import android.caged.employeemanagement.domain.model.Team

data class CreateTeamState(
    val teamName: String = "",
    val teamLeadId: Long = -1,
    val teams: Map<Long, Team> = emptyMap()
)
