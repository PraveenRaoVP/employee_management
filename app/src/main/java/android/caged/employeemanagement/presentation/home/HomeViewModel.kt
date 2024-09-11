package android.caged.employeemanagement.presentation.home

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localUserManager: LocalUserManager,
    private val applicationUseCases: ApplicationUseCases
) : ViewModel() {
//    val uiState = mutableStateOf(HomeState())
    // get recent employees, get employee count, get team count

    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> get() = _uiState


    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            val currentEmployee: Employee = applicationUseCases.getEmployeeById(localUserManager.credentials.first().first!!)!!
            if (currentEmployee.position == Position.ADMIN) {
                _uiState.value = _uiState.value.copy(
                    teamMap = applicationUseCases.getAllTeamsAsMap(),
                    employeeCount = applicationUseCases.getEmployeeCount(),
                    teamCount = applicationUseCases.getTeamCount(),
                    recentEmployees = applicationUseCases.getRecentEmployees()
                )

                for((teamID, team) in uiState.value.teamMap) {
                    _uiState.value = _uiState.value.copy(
                        employeeCountPerTeam = uiState.value.employeeCountPerTeam + (team to applicationUseCases.getEmployeeCountByTeam(teamID))
                    )
                }

            } else if (currentEmployee.position == Position.MANAGER) {
                _uiState.value = _uiState.value.copy(
                    teamMap = applicationUseCases.getAllTeamsAsMap(),
                    employeeCount = applicationUseCases.getEmployeeCountByTeam(currentEmployee.teamID!!),
                    teamCount = 1,
                    recentEmployees = applicationUseCases.getRecentEmployeesByTeamId(currentEmployee.teamID!!)
                )
            }
        }
    }

    fun onEmployeeDeleted(employee: Employee) {
        // Filter out the deleted employee from recentEmployees
        _uiState.value = _uiState.value.copy(
            recentEmployees = uiState.value.recentEmployees.filter { it.employeeId != employee.employeeId }
        )
    }
}