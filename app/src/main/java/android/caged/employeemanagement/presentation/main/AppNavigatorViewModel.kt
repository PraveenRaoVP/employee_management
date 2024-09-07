package android.caged.employeemanagement.presentation.main

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.EmployeeRepository
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.presentation.navgraph.Screen
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppNavigatorViewModel @Inject constructor(
    private val localUserManager: LocalUserManager,
    private val applicationUseCases: ApplicationUseCases
) : ViewModel() {
    val userType = mutableStateOf(Position.ADMIN)

    val currentUser = mutableStateOf(Employee(-1,"", Position.EMPLOYEE, "", 1.1, "", -1, "", ""))

    val currentUserTeam = mutableStateOf(Team(-1, "", -1))

    val previousScreen = mutableStateOf(Screen.HomeRoute.route)

    init {
        viewModelScope.launch {
            val userDetails = applicationUseCases.getEmployeeById(localUserManager.credentials.first().first!!)
            currentUser.value = userDetails!!
            currentUserTeam.value = applicationUseCases.getTeamDetailsById(userDetails!!.teamID)!!
            if(userDetails?.position == Position.ADMIN) {
                userType.value = Position.ADMIN
            } else if(userDetails?.position == Position.MANAGER) {
                userType.value = Position.MANAGER
            } else {
                userType.value = Position.EMPLOYEE
            }
        }
    }


}