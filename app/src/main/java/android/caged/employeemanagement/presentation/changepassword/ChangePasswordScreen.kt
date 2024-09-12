package android.caged.employeemanagement.presentation.changepassword

import android.caged.employeemanagement.presentation.components.ActionBar
import android.caged.employeemanagement.presentation.navgraph.navigateToPopUp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChangePasswordScreen(
    navigateToPopUp: (String, String) -> Unit,
    onBack: () -> Unit,
    state: ChangePasswordState,
    onEvent: (ChangePasswordEvent, (String, String) -> Unit) -> Unit
) {
    Scaffold(
        topBar = {
            ActionBar(
                title = "Change Password",
                isBackEnabled = true,
                onBackClick = onBack,
                onAddEmployeeClick = {})
        }
    ) { innerPadding ->
        val topPadding = innerPadding.calculateTopPadding()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text("Change Password")
            TextField(value = state.oldPassword, onValueChange = {
                onEvent(
                    ChangePasswordEvent.OldPasswordChanged(
                        it
                    ),
                    {_,_->}
                )
            }, placeholder = { Text("Old Password") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = state.newPassword, onValueChange = { onEvent(ChangePasswordEvent.NewPasswordChanged(it), {_,_->}) } , placeholder = { Text("New Password") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = state.confirmPassword, onValueChange = { onEvent(ChangePasswordEvent.ConfirmPasswordChanged(it),{_,_->}) }, placeholder = { Text("Confirm Password") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onEvent(ChangePasswordEvent.ChangePassword, navigateToPopUp) }) {
                Text("Submit")
            }
            if(state.error.isNotBlank()) {
                Text(state.error)
            }
        }
    }
}