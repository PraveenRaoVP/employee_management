package android.caged.employeemanagement.presentation.changepassword

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.ext.isValidPassword
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    val uiState = mutableStateOf(ChangePasswordState())

    fun onEvent(event: ChangePasswordEvent, navigateToPopUp: (String, String) -> Unit) {
        when(event) {
            ChangePasswordEvent.ChangePassword -> changePassword(navigateToPopUp)
            is ChangePasswordEvent.ConfirmPasswordChanged -> uiState.value = uiState.value.copy(confirmPassword = event.confirmPassword)
            is ChangePasswordEvent.NewPasswordChanged -> uiState.value = uiState.value.copy(newPassword = event.newPassword)
            is ChangePasswordEvent.OldPasswordChanged -> uiState.value = uiState.value.copy(oldPassword = event.oldPassword)
        }
    }

    private fun changePassword(navigateToPopUp: (String, String) -> Unit) {

        if(!uiState.value.newPassword.isValidPassword()) {
            uiState.value = uiState.value.copy(error = "Password must be at least 8 characters long")
            Log.i("ChangePasswordViewModel", "Password must be at least 8 characters long")
            return
        }
        if(uiState.value.newPassword != uiState.value.confirmPassword) {
            uiState.value = uiState.value.copy(error = "Passwords do not match")
            Log.i("ChangePasswordViewModel", "Passwords do not match")
            return
        }

        viewModelScope.launch {
            if(localUserManager.credentials.first().second!! == uiState.value.oldPassword) {
                uiState.value = uiState.value.copy(error = "")
                applicationUseCases.credentialUseCases.updatePassword(localUserManager.credentials.first().first!!, uiState.value.newPassword)
                localUserManager.saveCredentials(localUserManager.credentials.first().first!!, uiState.value.newPassword)
                Log.i("ChangePasswordViewModel", "Password changed from ${uiState.value.oldPassword} to ${uiState.value.newPassword}")
                withContext(Dispatchers.Main) {
                    navigateToPopUp(Screen.PostLogin.route, Screen.ChangePassword.route)
                }
            } else {
                uiState.value = uiState.value.copy(error = "Old password is incorrect")
                Log.i("ChangePasswordViewModel", "Old password is incorrect")
                Log.i("ChangePasswordViewModel", "Old password is ${localUserManager.credentials.first().second!!}")
                return@launch
            }
        }
    }
}