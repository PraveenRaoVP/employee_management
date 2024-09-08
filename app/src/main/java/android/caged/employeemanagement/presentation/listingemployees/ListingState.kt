package android.caged.employeemanagement.presentation.listingemployees

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Team

data class ListingState(
    val searchQuery: String = "",
    val results: List<Employee> = listOf(),
    val employees: List<Employee> = listOf(),
    val teamMap: Map<Long, Team> = mapOf(),
    val filterByTeamID: Long = -1
)
