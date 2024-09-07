package android.caged.employeemanagement.presentation.home

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.presentation.navgraph.Screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    navigateToPopUp: (String, String) -> Unit,
    navigateTo : (String) -> Unit,
    currentUser: Employee
) {
    if(currentUser.position == Position.ADMIN) {
        // TODO: Admin Screens
    }
    if(currentUser.position == Position.MANAGER) {
        // TODO: Manager Screen.
    }

    if(currentUser.position == Position.EMPLOYEE) {
        EmployeeHomeScreen(currentUser, navigateTo)
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