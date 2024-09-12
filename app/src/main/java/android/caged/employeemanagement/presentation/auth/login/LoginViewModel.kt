package android.caged.employeemanagement.presentation.auth.login

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.caged.employeemanagement.utils.returnHashedPassword
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localUserManager: LocalUserManager,
    private val applicationUseCases : ApplicationUseCases
) : ViewModel() {

    val state = mutableStateOf(LoginState())

    private val employeeId
        get() = state.value.employeeId

    private val password
        get() = state.value.password

    private fun onEmployeeIDChanged(employeeId : String) {
        state.value = state.value.copy(employeeId = employeeId)
    }

    private fun onPasswordChanged(password : String) {
        state.value = state.value.copy(password = password)
    }

    private fun clearErrors() {
        state.value = state.value.copy(isErrorEmpId = false, errorEmpId = "", isErrorPassword = false, errorPassword = "")
    }

    fun onEvent(event : LoginEvent, navigateToPopUp: (String, String) -> Unit) {
        when(event) {
            is LoginEvent.OnLoginClick -> {
                login(navigateToPopUp)
            }

            is LoginEvent.EmployeeIDChanged -> onEmployeeIDChanged(event.employeeID)
            is LoginEvent.PasswordChanged -> onPasswordChanged(event.password)
            is LoginEvent.ClearErrors -> clearErrors()
        }
    }

    private fun login(navigateToPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            try {
                employeeId.toLong()
            } catch (e: NumberFormatException) {
                state.value = state.value.copy(isErrorEmpId = true, errorEmpId = "Invalid Employee ID. Please enter a valid Employee ID.")
                state.value = state.value.copy(isLoading = false)
                return@launch
            }
            if(employeeId.isBlank() || applicationUseCases.employeeUseCases.findEmployeeById(employeeId.toLong()) == null) {
                state.value = state.value.copy(isErrorEmpId = true, errorEmpId = "Invalid Employee ID. Please enter a valid Employee ID.")
                state.value = state.value.copy(isLoading = false)
            }

            if(password.isBlank()) {
                state.value = state.value.copy(isErrorPassword = true, errorPassword = "Password cannot be empty.", isLoading = false)
                return@launch
            }

            if(applicationUseCases.credentialUseCases.getCredentials(employeeId.toLong())?.password != returnHashedPassword(password)) {
                state.value = state.value.copy(isErrorPassword = true, errorPassword = "Incorrect Password. Please enter the correct password.", isLoading = false)
            }

            if(!state.value.isErrorEmpId && !state.value.isErrorPassword) {

                // navigate to home screen
                localUserManager.saveCredentials(state.value.employeeId.toLong(), state.value.password)
                navigateToPopUp(Screen.PostLogin.route, Screen.LoginRoute.route)

                state.value = state.value.copy(isLoading = false)
            }
        }
    }
}