package android.caged.employeemanagement.presentation.common

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun EmployeeList(
    employeeList: List<Employee>,
    teamMap: Map<Long, Team>,
    onEmployeeClick: (Employee) -> Unit,
    shouldDelete: Boolean = false,
    onDeleteClicked: (Employee) -> Unit = {},
    currentUser: Employee = Employee(
        employeeId = -1,
        employeeName =  "Unknown",
        position = Position.ADMIN,
        designation = "",
        salary = -1.0,
        teamID = -1,
        email = "",
        phone = "",
        profileImageUrl = ""
        )
) {
    var deleteOrNot by remember {
        mutableStateOf(false)
    }
    LazyColumn {
        items(employeeList) { employee ->
            val team: Team = teamMap[employee.teamID] ?: Team(employee.teamID, "Unknown Team", -1)
            deleteOrNot = if(employee.employeeId == currentUser.employeeId && shouldDelete) {
                false
            } else if(employee.employeeId != currentUser.employeeId && shouldDelete) {
                true
            } else {
                false
            }

            Log.i("EmployeeList", "employeeId: ${employee.employeeId} deleteOrNot: $deleteOrNot")
            EmployeeCard(
                employee = employee,
                team = team,
                onClick = { onEmployeeClick(employee) },
                showDeleteButton = deleteOrNot,
                onDeleteClick = { onDeleteClicked(employee) }
            )
        }
    }
}