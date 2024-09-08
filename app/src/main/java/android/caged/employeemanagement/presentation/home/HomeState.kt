package android.caged.employeemanagement.presentation.home

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Team

data class HomeState(
    val employeeCount: Int = 0,
    val teamCount: Int = 0,
    val recentEmployees: List<Employee> = listOf(),
    val teamMap: Map<Long, Team> = mapOf()
)