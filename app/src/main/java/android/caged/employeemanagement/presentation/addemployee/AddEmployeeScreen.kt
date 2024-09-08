package android.caged.employeemanagement.presentation.addemployee

import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.presentation.common.TeamDropdownMenu
import android.caged.employeemanagement.presentation.components.ActionBar
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.caged.employeemanagement.presentation.navgraph.navigateToPopUp
import android.caged.employeemanagement.presentation.profilepage.ProfilePageEvent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEmployeeScreen(
    state: AddEmployeeState,
    teamState: CreateTeamState,
    teams: List<Team>,
    onEvent: (AddEmployeeEvent, (String, String) -> Unit) -> Unit,
    navigateToPopUp: (String, String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onEvent(AddEmployeeEvent.PhotoImageUrlChanged(it.toString()), { _, _ -> })
            }
        }

    val readStoragePermissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_MEDIA_IMAGES
    )

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = readStoragePermissionState.status) {
        readStoragePermissionState.launchPermissionRequest()
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val newProfileImageUrl = it.toString()

                // Update profile image URL in the Room database
                coroutineScope.launch {
                    onEvent(AddEmployeeEvent.PhotoImageUrlChanged(newProfileImageUrl), {_,_ ->})
                }
            }
        }
    )
    Scaffold(
        topBar = {
            ActionBar(
                title = "AddEmployee",
                isBackEnabled = true,
                onBackClick = { navigateToPopUp(Screen.PostLogin.route, Screen.AddEmployeeRoute.route) },
                isAddEmployeeButton = false,
                onAddEmployeeClick = {},
                isSettingsEnabled = false
            )
        },
    ) { innerPadding ->

        val topPadding = innerPadding.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Employee Name
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.employeeName,
                onValueChange = { onEvent(AddEmployeeEvent.EmployeeNameChanged(it), { _, _ -> }) },
                label = { Text("Employee Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Position Dropdown
            Row {
                Text(text = "Position: ${state.position.name}")
                PositionDropdownMenu(
                    selectedPosition = state.position,
                    onPositionSelected = { onEvent(AddEmployeeEvent.PositionChanged(it), { _, _ -> }) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.designation,
                onValueChange = { onEvent(AddEmployeeEvent.DesignationChanged(it), { _, _ -> }) },
                label = { Text("Designation") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.salary.toString(),
                onValueChange = { onEvent(AddEmployeeEvent.SalaryChanged(it), { _, _ -> }) },
                label = { Text("Salary") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.email,
                onValueChange = { onEvent(AddEmployeeEvent.EmailChanged(it), { _, _ -> }) },
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.phone,
                onValueChange = { onEvent(AddEmployeeEvent.PhoneChanged(it), { _, _ -> }) },
                label = { Text("Phone") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Team Dropdown

            var isCreateTeam = false

            if(state.position == Position.MANAGER || state.position == Position.ADMIN) {
                isCreateTeam = true
            }

            Row {
                Text(text = "${if(!teamState.showCreateTeam) "Team Name: ${teamState.teamName}" else ' '}")
                TeamDropdownMenu(
                    teams = teams,
                    selectedTeam = teams.find { it.teamID == state.teamId },
                    teamName = teamState.teamName,
                    onTeamSelected = { onEvent(AddEmployeeEvent.TeamChanged(it.teamID), { _, _ -> }) },
                    onTeamNameChanged = { onEvent(AddEmployeeEvent.TeamNameChanged(it), { _, _ -> }) },
                    onCreateNewTeam = {
                        onEvent(AddEmployeeEvent.TeamChanged(teamState.teamLeadId), { _, _ -> })
                    },
                    showIsCreateTeam = isCreateTeam,
                    onEvent = onEvent
                )
            }

            // if create new team is clicked, show a textfield asking for new team's name
            if (teamState.showCreateTeam) {
                TextField(
                    value = teamState.teamName,
                    onValueChange = { onEvent(AddEmployeeEvent.TeamNameChanged(it), { _, _ -> }) },
                    label = { Text("Team Name") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Photo picker
            Box(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(25.dp)
                )
            ) {
                AndroidView(
                    factory = { context ->
                        ImageView(context).apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            Glide.with(context)
                                .load(state.profileImageUrl)
                                .circleCrop()
                                .listener(object : RequestListener<Drawable> {
                                    override fun onResourceReady(
                                        resource: Drawable,
                                        model: Any,
                                        target: Target<Drawable>?,
                                        dataSource: DataSource,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        return false
                                    }

                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any?,
                                        target: Target<Drawable>,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        Log.i("Glide", "Image load failed", e)
                                        return false
                                    }
                                })
                                .into(this)
                        }
                    },
                    modifier = Modifier
                        .height(128.dp)
                        .clickable {
                            // Trigger the image picker when the image is clicked
                            imagePickerLauncher.launch("image/*")
                        }
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(50.dp)
                        )
                )
            }

            Button(onClick = {
                onEvent(AddEmployeeEvent.ClearError, { _, _ -> })
                onEvent(AddEmployeeEvent.CreateEmployee, navigateToPopUp)
            }) {
                Text("Create Employee")
            }

            if (state.error.isNotBlank()) {
                Text(state.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PositionDropdownMenu(
    selectedPosition: Position,
    onPositionSelected: (Position) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val positions = Position.entries.toTypedArray()

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp)) {
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
            positions.forEach { position ->
                DropdownMenuItem(
                    text = { Text(position.name) },
                    onClick = {
                        onPositionSelected(position)
                        expanded = false
                    }
                )
            }
        }
    }
}