package android.caged.employeemanagement.presentation.addemployee

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.ext.isValidEmail
import android.caged.employeemanagement.ext.isValidMobileNumber
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
            teamState.value = teamState.value.copy(teams = applicationUseCases.teamUseCases.getAllTeamsAsMap())
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
            is AddEmployeeEvent.CreateTeam -> createTeam()
            is AddEmployeeEvent.TeamNameChanged -> teamNameChanged(event.teamName)
            AddEmployeeEvent.CreateEmployee -> createEmployee(navigateToPopUp)
            AddEmployeeEvent.ClearError -> uiState.value = uiState.value.copy(error = "")
            is AddEmployeeEvent.ChangeTeamState -> teamState.value = teamState.value.copy(showCreateTeam = event.showCreateTeam)
        }
    }

    private fun employeeNameChange(name: String) {
        uiState.value = uiState.value.copy(employeeName = name)
    }

    private fun teamNameChanged(teamName: String) {
        teamState.value = teamState.value.copy(teamName = teamName)
    }

    private fun designationChange(designation: String) {
        uiState.value = uiState.value.copy(designation = designation)
    }

    private fun positionChange(position: Position) {
        uiState.value = uiState.value.copy(position = position)
    }

    private fun teamChanged(teamId: Long) {
        uiState.value = uiState.value.copy(teamId = teamId)
    }

    private fun salaryChanged(salary: String) {
        uiState.value = uiState.value.copy(salary = salary)
    }

    private fun emailChanged(email: String) {
        uiState.value = uiState.value.copy(email = email)
    }

    private fun phoneChanged(phone: String) {
        uiState.value = uiState.value.copy(phone = phone)
    }

    private fun photoImageUrlChanged(url: String) {
        uiState.value = uiState.value.copy(profileImageUrl = url)
    }

    private fun createTeam() {
        viewModelScope.launch {
            val teamName = teamState.value.teamName
            if(applicationUseCases.teamUseCases.getTeamByName(teamName) == null) {
                val teamId = applicationUseCases.teamUseCases.createTeam(Team(teamName = teamName, teamLeadID = -1))
                uiState.value = uiState.value.copy(teamId = teamId)
                teamState.value = teamState.value.copy(disableTeamCreateButton = true)
            } else {
                uiState.value = uiState.value.copy(error = "Team name already exists")
            }
        }
    }

    private fun createEmployee(navigateToPopUp: (String, String) -> Unit) {
        uiState.value = uiState.value.copy(error = "")
        if(uiState.value.employeeName.isEmpty() || uiState.value.designation.isEmpty() || uiState.value.email.isEmpty() || uiState.value.phone.isEmpty()) {
            uiState.value = uiState.value.copy(error = "Please fill all the fields")
            return
        }

        if(!uiState.value.email.isValidEmail()) {
            uiState.value = uiState.value.copy(error = "Please enter a valid email")
            return
        }

        if(!uiState.value.phone.isValidMobileNumber()) {
            uiState.value = uiState.value.copy(error = "Please enter a valid phone number")
            return
        }

        viewModelScope.launch {
            try {

                if(uiState.value.profileImageUrl.isBlank()) {
                    uiState.value = uiState.value.copy(profileImageUrl = "https://mastertondental.co.nz/wp-content/uploads/2022/12/team-profile-placeholder.jpg")
                }

                val employeeState = uiState.value

                // Step 1: Insert the employee into the database
                val newEmployee = Employee(
                    employeeName = employeeState.employeeName,
                    position = employeeState.position,
                    designation = employeeState.designation,
                    salary = employeeState.salary.toDouble(),
                    email = employeeState.email,
                    teamID = uiState.value.teamId,  // Initially, it could be -1 if a new team is being created
                    phone = employeeState.phone,
                    profileImageUrl = employeeState.profileImageUrl
                )

                val generatedEmployeeId = applicationUseCases.employeeUseCases.insertEmployee(newEmployee)

                if(generatedEmployeeId != -1L) {
                    applicationUseCases.teamUseCases.updateTeamLeadIDInTeam(teamId = uiState.value.teamId, teamLeadId = generatedEmployeeId)
                } else {
                    Log.i("Employee ID","Employee ID: $generatedEmployeeId")
                }
                // create credentials for the employee
                applicationUseCases.credentialUseCases.insertCredentials(employeeID = generatedEmployeeId, password = "password")

                // Step 4: Navigate to a success pop-up or another screen
                navigateToPopUp(Screen.PostLogin.route, Screen.AddEmployeeRoute.route)
            } catch (e: Exception) {
                // Handle any errors during the process
                uiState.value = uiState.value.copy(error = "An error occurred: ${e.message}")
            }
        }
    }
}