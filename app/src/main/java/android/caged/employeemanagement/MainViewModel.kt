package android.caged.employeemanagement

import android.caged.employeemanagement.data.manager.LocalUserManagerImpl
import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Credentials
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.CredentialsRepository
import android.caged.employeemanagement.domain.repository.EmployeeRepository
import android.caged.employeemanagement.domain.repository.TeamRepository
import android.caged.employeemanagement.domain.usecases.appentry.SaveCredentialsEntry
import android.caged.employeemanagement.presentation.navgraph.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    localUserManager: LocalUserManager,
    employeeRepository: EmployeeRepository,
    credentialsRepository: CredentialsRepository,
    teamRepository: TeamRepository
) : ViewModel() {

    var startDestination by mutableStateOf(Screen.AppStartNavigation.route)
        private set

    init {
        // if the credentials are not null
        viewModelScope.launch {

//            teamRepository.insertTeam(
//                Team(
//                    teamID = 1,
//                    teamName = "Team A",
//                    teamLeadID = 1
//                )
//            )
//
//            employeeRepository.insertEmployee(
//                Employee(
//                    employeeId = 0,
//                    employeeName = "John Doe",
//                    position = Position.ADMIN,
//                    designation = "Software Engineer",
//                    salary = 1000.0,
//                    email = "johndoe@gmail.com",
//                    teamID = 1,
//                    phone = "1234567890",
//                    profileImageUrl = "https://www.google.com"
//                )
//            )
//
//            credentialsRepository.insertCredentials(
//                Credentials(
//                    credentialID = 1,
//                    employeeID = 1,
//                    password = "password"
//                )
//            )

            localUserManager.credentials.collect { credentials ->
                if (credentials.first != null && credentials.second != null) {
                    startDestination = Screen.PostLogin.route
                } else {
                    startDestination = Screen.AppStartNavigation.route
                }
            }
        }
    }
}