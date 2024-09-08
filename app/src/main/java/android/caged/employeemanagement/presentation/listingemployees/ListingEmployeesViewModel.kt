package android.caged.employeemanagement.presentation.listingemployees

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
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
    private val localUserManager: LocalUserManager
) : ViewModel() {

    val uiState = mutableStateOf(ListingState())

    init {
        viewModelScope.launch {
            uiState.value = uiState.value.copy(teamMap = applicationUseCases.getAllTeamsAsMap())
            val currentUser = applicationUseCases.getEmployeeById(localUserManager.credentials.first().first!!)
            if(currentUser?.position == Position.ADMIN) {
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

    fun onEvent(event: ListingEvent) {
        when(event) {
            is ListingEvent.Search -> {
                viewModelScope.launch {
                    if (event.query.isEmpty()) {
                        if (uiState.value.filterByTeamID != -1L) {
                            uiState.value = uiState.value.copy(
                                results = applicationUseCases.getEmployeesByTeamID(uiState.value.filterByTeamID)
                            )
                        } else {
                            uiState.value = uiState.value.copy(results = applicationUseCases.getAllEmployees())
                        }
                    } else {
                        // Search based on query
                        if (uiState.value.filterByTeamID != -1L) {
                            uiState.value = uiState.value.copy(
                                results = applicationUseCases.searchEmployeeByTeam(uiState.value.searchQuery, uiState.value.filterByTeamID)
                            )
                        } else {
                            uiState.value = uiState.value.copy(results = applicationUseCases.searchEmployee(uiState.value.searchQuery))
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
            }

            is ListingEvent.DeleteEmployee -> {
                viewModelScope.launch {
                    applicationUseCases.deleteEmployeeByID(event.employee.employeeId)
                    if(applicationUseCases.getEmployeeCountByTeam(event.employee.teamID) == 0 ) {
                        // delete team if there are no members present
                        applicationUseCases.deleteTeam(event.employee.teamID)
                    }
                    if (uiState.value.filterByTeamID != -1L) {
                        uiState.value = uiState.value.copy(
                            results = applicationUseCases.getEmployeesByTeamID(uiState.value.filterByTeamID)
                        )
                    } else {
                        uiState.value = uiState.value.copy(results = applicationUseCases.getAllEmployees())
                    }
                }
            }
        }
    }
}