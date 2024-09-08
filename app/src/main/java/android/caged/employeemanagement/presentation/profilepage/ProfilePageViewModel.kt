package android.caged.employeemanagement.presentation.profilepage

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.domain.usecases.application.GetTeamDetailsById
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    val uiState = mutableStateOf(ProfilePageState())
    val userId = mutableStateOf(-1L)

    val employee = mutableStateOf<Employee?>(null)
    val teamName = mutableStateOf("")

    fun onEvent(event: ProfilePageEvent) {
        when (event) {
            is ProfilePageEvent.UpdateProfileImageUrl -> {
                viewModelScope.launch {
                    uiState.value = uiState.value.copy(profileImageUrl = event.newProfileImageUrl)
                    applicationUseCases.updateProfileImage(userId.value, event.newProfileImageUrl)
                    setUserId(userId.value)
                    refreshEmployee() // Refresh the employee data to reflect the changes
                }
            }

            is ProfilePageEvent.SetUserId -> {
                getEmployee(event.userId)
            }

            is ProfilePageEvent.SetProfileImageUrl -> {
                uiState.value = uiState.value.copy(profileImageUrl = event.profileImageUrl)
            }
        }
    }

    fun setUserId(userId: Long) {
        this.userId.value = userId
    }

    private fun getEmployee(employeeId: Long) {
        viewModelScope.launch {
            val fetchedEmployee = applicationUseCases.getEmployeeById(employeeId)
            employee.value = fetchedEmployee
            fetchedEmployee?.teamID?.let {
                getTeamName(it)
            }
        }
    }

    private fun getTeamName(teamId: Long) {
        viewModelScope.launch {
            teamName.value = applicationUseCases.getTeamById(teamId)?.teamName ?: "No team"
        }
    }

    private fun refreshEmployee() {
        getEmployee(userId.value)
    }
}
