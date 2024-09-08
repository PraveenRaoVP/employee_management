package android.caged.employeemanagement.presentation.common

import android.caged.employeemanagement.domain.model.Team
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDropdownMenu(
    teams: List<Team>,
    selectedTeam: Team?,
    teamName: String,
    onTeamSelected: (Team) -> Unit,
    onTeamNameChanged: (String) -> Unit,
    onCreateNewTeam: () -> Unit,
    showIsCreateTeam: Boolean = false
) {
//    var expanded by remember { mutableStateOf(false) }
//    var selectedText by remember { mutableStateOf(selectedTeam?.teamName ?: "Select a Team") }
//    var isCreatingTeam by remember { mutableStateOf(false) }
//
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded } // Toggle the dropdown expansion
//    ) {
//        TextField(
//            readOnly = true,
//            value = selectedText,
//            onValueChange = { /* No-op since it's read-only */ },
//            label = { Text("Team") },
//            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    Log.i("TeamDropdownMenu", "Dropdown clicked") // Logging statement
//                    expanded = true
//                } // Ensure the TextField can open the dropdown
//        )
//
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false } // Close dropdown when item is selected
//        ) {
//            teams.forEach { team ->
//                DropdownMenuItem(
//                    text = { Text(team.teamName) },
//                    onClick = {
//                        Log.i("TeamDropdownMenu", "Team selected: ${team.teamName}")
//                        selectedText = team.teamName
//                        onTeamSelected(team)
//                        expanded = false // Close the dropdown after selection
//                    }
//                )
//            }
//            if (showIsCreateTeam) {
//                DropdownMenuItem(
//                    text = { Text("Create New Team") },
//                    onClick = {
//                        isCreatingTeam = true
//                        expanded = false // Close dropdown and show creation UI
//                    }
//                )
//            }
//        }
//    }
//
//    // Display team creation UI if triggered
//    if (isCreatingTeam) {
//        Column(
//            modifier = Modifier.padding(8.dp)
//        ) {
//            TextField(
//                value = teamName,
//                onValueChange = onTeamNameChanged,
//                label = { Text("New Team Name") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Button(
//                onClick = {
//                    onCreateNewTeam()
//                    isCreatingTeam = false // Reset creation state
//                }
//            ) {
//                Text("Create Team")
//            }
//        }
//    }
    val context = LocalContext.current
    var selectedText by remember { mutableStateOf(selectedTeam?.teamName ?: "Select a Team") }
    var expanded by remember {
        mutableStateOf(false)
    }
    var isCreatingTeam by remember { mutableStateOf(false) }


    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "More"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = { Text(text = "None") }, onClick = {
                selectedText = "None"
                onTeamSelected(Team(-1L, "None", -1))
                expanded = false
            })
            teams.forEach { team ->
                DropdownMenuItem(
                    text = { Text(team.teamName) },
                    onClick = {
                        Log.i("TeamDropdownMenu", "Team selected: ${team.teamName}")
                        selectedText = team.teamName
                        onTeamSelected(team)
                        expanded = false // Close the dropdown after selection
                    }
                )
            }

            if (showIsCreateTeam) {
                DropdownMenuItem(
                    text = { Text("Create New Team") },
                    onClick = {
                        isCreatingTeam = true
                        expanded = false // Close dropdown and show creation UI
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamDropdownMenuPreview() {
    val teams = listOf(
        Team(1, "Team 1", 1),
        Team(2, "Team 2", 2),
        Team(3, "Team 3", 3)
    )
    TeamDropdownMenu(
        teams,
        null,
        teamName = "Team A",
        onTeamSelected = {},
        onTeamNameChanged = {},
        onCreateNewTeam = {})
}