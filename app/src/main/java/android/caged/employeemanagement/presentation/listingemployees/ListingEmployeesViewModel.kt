package android.caged.employeemanagement.presentation.listingemployees

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.presentation.home.HomeViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingEmployeesViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases,
    private val localUserManager: LocalUserManager,
) : ViewModel() {

    val uiState = mutableStateOf(ListingState())

    val shouldDelete = mutableStateOf(false)

    init {
        viewModelScope.launch {
            getTeams()
            val currentUser = applicationUseCases.getEmployeeById(localUserManager.credentials.first().first!!)
            if(currentUser?.position == Position.ADMIN) {
                shouldDelete.value = true
                uiState.value = uiState.value.copy(results = applicationUseCases.getAllEmployees())
                uiState.value = uiState.value.copy(employees = applicationUseCases.getAllEmployees())
            } else if(currentUser?.position == Position.MANAGER) {
                uiState.value = uiState.value.copy(results = applicationUseCases.getEmployeesByTeamID(
                    currentUser.teamID
                ))
                uiState.value = uiState.value.copy(employees = applicationUseCases.getEmployeesByTeamID(
                    currentUser.teamID
                ))
            }
        }
    }

    fun getTeams() {
        viewModelScope.launch {
            uiState.value = uiState.value.copy(teamMap = applicationUseCases.getAllTeamsAsMap())
        }
    }

    fun onEvent(event: ListingEvent) {
        when(event) {
            is ListingEvent.Search -> {
                viewModelScope.launch {
                    if (event.query.isEmpty()) {
                        if (uiState.value.filterByTeamID != -1L) {
                            uiState.value = uiState.value.copy(
                                results = applicationUseCases.getEmployeesByTeamID(uiState.value.filterByTeamID)
                            )
                            Log.i("ListingEmployeesViewModel", "Search query empty: ${uiState.value.results}")
                        } else {
                            uiState.value = uiState.value.copy(results = applicationUseCases.getAllEmployees())
                            Log.i("ListingEmployeesViewModel", "Search query empty: ${uiState.value.results}")
                        }
                    } else {
                        // Search based on query
                        if (uiState.value.filterByTeamID != -1L) {
                            uiState.value = uiState.value.copy(
                                results = applicationUseCases.searchEmployeeByTeam(uiState.value.searchQuery, uiState.value.filterByTeamID)
                            )
                            Log.i("ListingEmployeesViewModel", "Search query not empty: ${uiState.value.results}")
                        } else {
                            uiState.value = uiState.value.copy(results = applicationUseCases.searchEmployee(uiState.value.searchQuery))
                            Log.i("ListingEmployeesViewModel", "Search query not empty: ${uiState.value.results}")
                        }
                    }
                }
            }
            is ListingEvent.FilterTeam -> {
                viewModelScope.launch {
                    uiState.value = uiState.value.copy(
                        results = applicationUseCases.searchEmployeeByTeam(uiState.value.searchQuery, event.teamID),
                        filterByTeamID = event.teamID
                    )
                }
            }

            is ListingEvent.UpdateSearchQuery -> {
                uiState.value = uiState.value.copy(searchQuery = event.searchQuery)
                Log.i("ListingEmployeesViewModel", "Search query updated: ${event.searchQuery}")
            }

            is ListingEvent.DeleteEmployee -> {
                viewModelScope.launch {
                    applicationUseCases.deleteEmployeeByID(event.employee.employeeId)

                    if(applicationUseCases.getEmployeeCountByTeam(event.employee.teamID) == 0 ) {
                        // delete team if there are no members present
                        applicationUseCases.deleteTeam(event.employee.teamID)
                    }

                    applicationUseCases.deleteCredentials(event.employee.employeeId)

                    getTeams()

                    // Refresh the results list after deletion
                    val updatedEmployeeList = if (uiState.value.filterByTeamID != -1L) {
                        applicationUseCases.getEmployeesByTeamID(uiState.value.filterByTeamID)
                    } else {
                        applicationUseCases.getAllEmployees()
                    }

                    // Update the UI state with the new list
                    uiState.value = uiState.value.copy(results = updatedEmployeeList, employees = updatedEmployeeList)
                }
            }

            ListingEvent.GetTeams -> getTeams()
        }
    }
}