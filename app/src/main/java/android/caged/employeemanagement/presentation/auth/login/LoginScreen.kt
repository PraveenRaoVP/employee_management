package android.caged.employeemanagement.presentation.auth.login

import android.caged.employeemanagement.presentation.common.EmployeeIDField
import android.caged.employeemanagement.presentation.common.PasswordField
import android.caged.employeemanagement.presentation.navgraph.navigateToPopUp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onEvent: (LoginEvent, (String, String) -> Unit) -> Unit,
    state: LoginState,
    navigateToPopUp: (String, String) -> Unit
) {
    Scaffold { innerPadding ->
        val padding = innerPadding.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Employee Management System")

            Spacer(modifier = Modifier.height(16.dp))

            EmployeeIDField(
                value = state.employeeId,
                onNewValue = {
                    onEvent(LoginEvent.ClearErrors) { _, _ -> }
                    onEvent(LoginEvent.EmployeeIDChanged(it)) { _,_ -> }
                },
                isError = state.isErrorEmpId,
            )

            if (state.isErrorEmpId) {
                Text(
                    state.errorEmpId,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = state.password,
                onNewValue = {
                    onEvent(LoginEvent.ClearErrors) { _, _ -> }
                    onEvent(LoginEvent.PasswordChanged(it)) { _, _ -> }
                })
            if(state.isErrorPassword) {
                Text(
                    state.errorPassword,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                onEvent(LoginEvent.ClearErrors, {_,_ ->})
                onEvent(LoginEvent.OnLoginClick, navigateToPopUp)
            }) {
                Text(text = "Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onEvent = { _,_ -> }, state = LoginState(), navigateToPopUp = {_,_ -> })
}