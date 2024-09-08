package android.caged.employeemanagement.presentation.home

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.presentation.common.EmployeeList
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    navigateToPopUp: (String, String) -> Unit,
    navigateTo: (String) -> Unit,
    currentUser: Employee,
    state: HomeState
) {
    if (currentUser.position == Position.ADMIN) {
        AdminDashboardScreen(
            employeeCount = state.employeeCount,
            teamCount = state.teamCount,
            recentEmployees = state.recentEmployees,
            teamMap = state.teamMap
        )
    }
    if (currentUser.position == Position.MANAGER) {
        // TODO: Manager Screen.
    }

    if (currentUser.position == Position.EMPLOYEE) {
        EmployeeHomeScreen(currentUser, navigateTo)
    }
}

@Composable
fun AdminDashboardScreen(
    employeeCount: Int,
    teamCount: Int,
    recentEmployees: List<Employee>,
    teamMap: Map<Long, Team>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dashboard", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Total Employees: $employeeCount", style = MaterialTheme.typography.bodyLarge)
        Text("Total Teams: $teamCount", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text("New Employees Joined", style = MaterialTheme.typography.titleMedium)
        Log.i("AdminDashboardScreen", "Recent Employees: $recentEmployees")

        EmployeeList(employeeList = recentEmployees, teamMap = teamMap, onEmployeeClick = {})

        // Optional: Add a graph to show employee count per team using a third-party library
    }
}


@Composable
private fun EmployeeHomeScreen(
    currentUser: Employee,
    navigateTo: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome ${currentUser.employeeName}!")
        Button(onClick = { navigateTo(Screen.ProfileRoute.route) }) {
            Text("Profile")
        }
    }
}