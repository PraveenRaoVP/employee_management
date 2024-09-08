package android.caged.employeemanagement.presentation.listingemployees

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.presentation.common.EmployeeList
import android.caged.employeemanagement.presentation.common.SearchBar
import android.caged.employeemanagement.presentation.common.TeamDropdownMenu
import android.caged.employeemanagement.presentation.home.HomeViewModel
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.selects.select

@Composable
fun ListingEmployeesScreen(
    state: ListingState,
    onEvent: (ListingEvent) -> Unit,
    navigate: (Employee) -> Unit,
    shouldDelete: Boolean = false,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        SearchBar(
            text = state.searchQuery,
            onSearch = { onEvent(ListingEvent.Search(state.searchQuery)) },
            onValueChange = {
                onEvent(ListingEvent.UpdateSearchQuery(it))
                onEvent(ListingEvent.Search(it))
            },
            onClear = {
                onEvent(ListingEvent.UpdateSearchQuery(""))
                onEvent(ListingEvent.Search(""))
            },
            readOnly = false
        )

        val selectedTeam = if (state.filterByTeamID != -1L) {
            state.teamMap[state.filterByTeamID]
        } else {
            null // No team selected
        }
        Log.i("ListingEmployeesScreen", "teamMap: ${state.teamMap}")
        // Team Dropdown Menu
        if(shouldDelete) {
            Row {
                Text(text = "Filter by Team: ${selectedTeam?.teamName ?: "None"}")
                TeamDropdownMenu(
                    teams = state.teamMap.values.toList(),
                    selectedTeam = selectedTeam,
                    teamName = "",
                    onTeamSelected = { team ->
                        onEvent(ListingEvent.FilterTeam(team.teamID))
                    },
                    onTeamNameChanged = {},
                    onCreateNewTeam = {},
                    showIsCreateTeam = false, // Set to true if you want to allow creating new teams
                    onEvent = { _, _ -> } // No-op
                )
            }
        }

        val filteredEmployeeList = if (state.filterByTeamID == -1L) {
            state.employees // Show all employees if no team is selected
        } else {
            state.results // Filtered list based on team selection
        }

        EmployeeList(
            employeeList = filteredEmployeeList,
            teamMap = state.teamMap,
            onEmployeeClick = navigate,
            shouldDelete = shouldDelete,
            onDeleteClicked = { employee ->
                onEvent(ListingEvent.DeleteEmployee(employee))
                homeViewModel.refreshData()
                homeViewModel.refreshData()
            }
        )
    }
}