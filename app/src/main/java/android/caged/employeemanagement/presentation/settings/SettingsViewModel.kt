package android.caged.employeemanagement.presentation.settings

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.presentation.navgraph.Screen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localUserManager: LocalUserManager
) : ViewModel() {
    fun signOut(navigateToPopUp : (String, String) -> Unit) {
        viewModelScope.launch {
            localUserManager.clearCredentials()
            navigateToPopUp(Screen.AppStartNavigation.route, Screen.PostLogin.route)
        }
    }
}