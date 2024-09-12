package android.caged.employeemanagement.presentation.listingemployees

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingEmployeesViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    // Replace mutableStateOf with MutableStateFlow
    private val _uiState = MutableStateFlow(ListingState())
    val uiState: StateFlow<ListingState> get() = _uiState

    private val _shouldDelete = MutableStateFlow(false)
    val shouldDelete: StateFlow<Boolean> get() = _shouldDelete

    init {
        viewModelScope.launch {
            getTeams()
            val currentUser = applicationUseCases.employeeUseCases.getEmployeeById(localUserManager.credentials.first().first!!).first()
            if (currentUser?.position == Position.ADMIN) {
                _shouldDelete.value = true
                _uiState.value = _uiState.value.copy(results = applicationUseCases.employeeUseCases.getAllEmployees().first())
                _uiState.value = _uiState.value.copy(employees = applicationUseCases.employeeUseCases.getAllEmployees().first())
            } else if (currentUser?.position == Position.MANAGER) {
                _uiState.value = _uiState.value.copy(results = applicationUseCases.employeeUseCases.getEmployeesByTeamID(currentUser.teamID).first())
                _uiState.value = _uiState.value.copy(employees = applicationUseCases.employeeUseCases.getEmployeesByTeamID(currentUser.teamID).first())
            }
        }
    }

    fun getTeams() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(teamMap = applicationUseCases.teamUseCases.getAllTeamsAsMap())
        }
    }

    fun onEvent(event: ListingEvent) {
        when (event) {
            is ListingEvent.Search -> {
                viewModelScope.launch {
                    if (event.query.isEmpty()) {
                        if (_uiState.value.filterByTeamID != -1L) {
                            applicationUseCases.employeeUseCases.getEmployeesByTeamID(_uiState.value.filterByTeamID).collect {
                                _uiState.value = _uiState.value.copy(results = it)
                            }

                        } else {
                            applicationUseCases.employeeUseCases.getAllEmployees().collect {
                                _uiState.value = _uiState.value.copy(results = it)
                            }
                        }
                    } else {
                        if (_uiState.value.filterByTeamID != -1L) {
                            applicationUseCases.employeeUseCases.searchEmployeeByTeam(_uiState.value.searchQuery, _uiState.value.filterByTeamID).collect {
                                _uiState.value = _uiState.value.copy(results = it)
                            }
//                            _uiState.value = _uiState.value.copy(results = applicationUseCases.searchEmployeeByTeam(_uiState.value.searchQuery, _uiState.value.filterByTeamID))

                        } else {
                            applicationUseCases.employeeUseCases.searchEmployee(_uiState.value.searchQuery).collect {
                                _uiState.value = _uiState.value.copy(results = it)
                            }
//                            _uiState.value = _uiState.value.copy(results = applicationUseCases.searchEmployee(_uiState.value.searchQuery))
                        }
                    }
                }
            }
            is ListingEvent.FilterTeam -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        results = applicationUseCases.employeeUseCases.searchEmployeeByTeam(_uiState.value.searchQuery, event.teamID).first(),
                        filterByTeamID = event.teamID
                    )
                }
            }

            is ListingEvent.UpdateSearchQuery -> {
                _uiState.value = _uiState.value.copy(searchQuery = event.searchQuery)
            }

            is ListingEvent.DeleteEmployee -> {
                viewModelScope.launch {
                    applicationUseCases.employeeUseCases.deleteEmployeeByID(event.employee.employeeId)

                    if (applicationUseCases.employeeUseCases.getEmployeeCountByTeam(event.employee.teamID) == 0) {
                        // delete team if there are no members present
                        applicationUseCases.teamUseCases.deleteTeam(event.employee.teamID)
                    }

                    applicationUseCases.credentialUseCases.deleteCredentials(event.employee.employeeId)

                    getTeams()

                    // Refresh the results list after deletion
                    val updatedEmployeeList = if (_uiState.value.filterByTeamID != -1L) {
                        applicationUseCases.employeeUseCases.getEmployeesByTeamID(_uiState.value.filterByTeamID)
                    } else {
                        applicationUseCases.employeeUseCases.getAllEmployees()
                    }

                    // Update the UI state with the new list
                    updatedEmployeeList.collect {
                        _uiState.value = _uiState.value.copy(results = it, employees = it)
                    }
//                    _uiState.value = _uiState.value.copy(results = updatedEmployeeList, employees = updatedEmployeeList)
                }
            }

            ListingEvent.GetTeams -> getTeams()
        }
    }
}