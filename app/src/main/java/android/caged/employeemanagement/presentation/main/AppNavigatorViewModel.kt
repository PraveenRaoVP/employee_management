package android.caged.employeemanagement.presentation.main

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.repository.EmployeeRepository
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.util.Log
import androidx.compose.runtime.collectAsState
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
            try {
                // Delete teams with no employees
                val teamMap = applicationUseCases.teamUseCases.getAllTeamsAsMap()
                for((key, value) in teamMap) {
                    if(applicationUseCases.employeeUseCases.getEmployeeCountByTeam(key) == 0) {
                        applicationUseCases.teamUseCases.deleteTeam(key)
                    }
                }

                val credentials = localUserManager.credentials.first()
                val userId = credentials.first ?: throw IllegalStateException("User ID is null")
                val userDetails = applicationUseCases.employeeUseCases.getEmployeeById(userId)
                if (userDetails != null) {
                    userDetails.collect {
                        currentUser.value = it!!
                        val teamDetails = applicationUseCases.teamUseCases.getTeamDetailsById(it.teamID)
                        if (teamDetails != null) {
                            currentUserTeam.value = teamDetails
                        } else {
                            Log.i("AppNavigatorViewModel", "Team details could not be retrieved")
                        }
                        userType.value = when (it.position) {
                            Position.ADMIN -> Position.ADMIN
                            Position.MANAGER -> Position.MANAGER
                            else -> Position.EMPLOYEE
                        }
                    }
//                    currentUser.value = userDetails.collectAsState().value!!

                } else {
                    // Handle case where userDetails is null
                    throw IllegalStateException("User details could not be retrieved")
                }
            } catch (e: Exception) {
                // Handle exceptions and possibly log the error
                e.printStackTrace()
                // Optionally, you can show a user-friendly error message or fallback UI
            }
        }
    }


}