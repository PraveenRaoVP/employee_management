package android.caged.employeemanagement.presentation.settings

import android.caged.employeemanagement.presentation.components.ActionBar
import android.caged.employeemanagement.presentation.navgraph.Screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(
    navigatePopUp : (String, String) -> Unit,
//    previousScreen: String,
    signOut : ((String, String) -> Unit) -> Unit
) {
    Scaffold(
        topBar = {
            ActionBar(title = "Settings", isBackEnabled = true, onBackClick = {
                navigatePopUp(
                    Screen.PostLogin.route,
                    Screen.SettingsRoute.route
                )
            }, onAddEmployeeClick = {})
        }
    ) { innerPadding ->
        val topPadding = innerPadding.calculateTopPadding()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { signOut(navigatePopUp) }) {
                Text("Sign Out")
            }
            Button(onClick = { navigatePopUp(Screen.ChangePassword.route, Screen.SettingsRoute.route) }) {
                Text("Change Password")
            }
        }
    }
}