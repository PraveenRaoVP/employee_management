package android.caged.employeemanagement.presentation.addemployee

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases
) : ViewModel(){
    val uiState = mutableStateOf(AddEmployeeState())
    val teamState = mutableStateOf(CreateTeamState())

    init {
        viewModelScope.launch {
            teamState.value = teamState.value.copy(teams = applicationUseCases.getAllTeamsAsMap())
        }
    }

    fun onEvent(event: AddEmployeeEvent, navigateToPopUp: (String, String) -> Unit) {
        when(event) {
            is AddEmployeeEvent.DesignationChanged -> designationChange(event.designation)
            is AddEmployeeEvent.EmployeeNameChanged -> employeeNameChange(event.name)
            is AddEmployeeEvent.PositionChanged -> positionChange(event.position)
            is AddEmployeeEvent.TeamChanged -> teamChanged(event.teamId)
            is AddEmployeeEvent.SalaryChanged -> salaryChanged(event.salary)
            is AddEmployeeEvent.EmailChanged -> emailChanged(event.email)
            is AddEmployeeEvent.PhoneChanged -> phoneChanged(event.phone)
            is AddEmployeeEvent.PhotoImageUrlChanged -> photoImageUrlChanged(event.imageUrl)
            is AddEmployeeEvent.CreateTeam -> TODO()
            is AddEmployeeEvent.TeamNameChanged -> teamNameChanged(event.teamName)
            AddEmployeeEvent.CreateEmployee -> createEmployee(navigateToPopUp)
            AddEmployeeEvent.ClearError -> uiState.value = uiState.value.copy(error = "")
            is AddEmployeeEvent.ChangeTeamState -> teamState.value = teamState.value.copy(showCreateTeam = event.showCreateTeam)
        }
    }

    fun employeeNameChange(name: String) {
        uiState.value = uiState.value.copy(employeeName = name)
    }

    fun teamNameChanged(teamName: String) {
        teamState.value = teamState.value.copy(teamName = teamName)
    }

    fun designationChange(designation: String) {
        uiState.value = uiState.value.copy(designation = designation)
    }

    fun positionChange(position: Position) {
        uiState.value = uiState.value.copy(position = position)
    }

    fun teamChanged(teamId: Long) {
        uiState.value = uiState.value.copy(teamId = teamId)
    }

    fun salaryChanged(salary: String) {
        uiState.value = uiState.value.copy(salary = salary)
    }

    fun emailChanged(email: String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun phoneChanged(phone: String) {
        uiState.value = uiState.value.copy(phone = phone)
    }

    fun photoImageUrlChanged(url: String) {
        uiState.value = uiState.value.copy(profileImageUrl = url)
    }

//    fun createTeam() {
//        viewModelScope.launch {
//            val teamName = teamState.value.teamName
//            if(applicationUseCases.getTeamByName(teamName) == null) {
//                // Assume `applicationUseCases.createTeam` adds the team to the database.
//                val teamId = applicationUseCases.createTeam(teamName, uiState.value.employeeId)
//                uiState.value = uiState.value.copy(teamId = teamId)
//            } else {
//                uiState.value = uiState.value.copy(error = "Team name already exists")
//            }
//        }
//    }

    fun createEmployee(navigateToPopUp: (String, String) -> Unit) {
        uiState.value = uiState.value.copy(error = "")
        if(uiState.value.employeeName.isEmpty() || uiState.value.designation.isEmpty() || uiState.value.email.isEmpty() || uiState.value.phone.isEmpty() || uiState.value.profileImageUrl.isEmpty()) {
            uiState.value = uiState.value.copy(error = "Please fill all the fields")
            return
        }

        viewModelScope.launch {
            try {
                val employeeState = uiState.value
                var teamId = uiState.value.teamId

                // Step 1: Insert the employee into the database
                val newEmployee = Employee(
                    employeeName = employeeState.employeeName,
                    position = employeeState.position,
                    designation = employeeState.designation,
                    salary = employeeState.salary.toDouble(),
                    email = employeeState.email,
                    teamID = teamId,  // Initially, it could be -1 if a new team is being created
                    phone = employeeState.phone,
                    profileImageUrl = employeeState.profileImageUrl
                )

                val generatedEmployeeId = applicationUseCases.insertEmployee(newEmployee)

                // Step 2: If a new team is being created, create the team with this employee as the team lead
                if (uiState.value.teamId == -1L) {  // Assuming -1 indicates a new team needs to be created
                    val newTeam = Team(teamName = teamState.value.teamName, teamLeadID = generatedEmployeeId)
                    applicationUseCases.createTeam(newTeam)
                    val team = applicationUseCases.getTeamByName(teamState.value.teamName)
                    Log.i("Team being created","Team ID: ${team?.teamID ?: -1}")
                    uiState.value = uiState.value.copy(teamId = team?.teamID!!)
                    if(team?.teamID != -1L) {
                        applicationUseCases.updateTeamIDInEmployee(employeeId = generatedEmployeeId, teamId = team.teamID)
                        Log.i("User updated","Team ID: $teamId")
                    }
                }

                // create credentials for the employee
                applicationUseCases.insertCredentials(employeeId = generatedEmployeeId, password = "password")

                // Step 4: Navigate to a success pop-up or another screen
                navigateToPopUp(Screen.PostLogin.route, Screen.AddEmployeeRoute.route)
            } catch (e: Exception) {
                // Handle any errors during the process
                uiState.value = uiState.value.copy(error = "An error occurred: ${e.message}")
            }
        }
    }
}