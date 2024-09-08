package android.caged.employeemanagement.presentation.common

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Team
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun EmployeeList(
    employeeList: List<Employee>,
    teamMap: Map<Long, Team>,
    onEmployeeClick: (Employee) -> Unit,
    shouldDelete: Boolean = false,
    onDeleteClicked: (Employee) -> Unit = {}
) {
    LazyColumn {
        items(employeeList) { employee ->
            val team: Team = teamMap[employee.teamID] ?: Team(employee.teamID, "Unknown Team", -1)
            EmployeeCard(
                employee = employee,
                team = team,
                onClick = { onEmployeeClick(employee) },
                showDeleteButton = shouldDelete,
                onDeleteClick = { onDeleteClicked(employee) }
            )
        }
    }
}