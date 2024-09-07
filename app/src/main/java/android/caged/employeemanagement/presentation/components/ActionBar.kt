package android.caged.employeemanagement.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBar(
    title: String,
    isBackEnabled: Boolean = false,
    onBackClick: () -> Unit = {},
    isSettingsEnabled: Boolean = true,
    onSettingsClick: () -> Unit = {},
    isAddEmployeeButton: Boolean = false,
    onAddEmployeeClick: () -> Unit
) {
    TopAppBar(title = { Text(text = title) },
        navigationIcon = {
            if(isBackEnabled) {
                IconButton(
                    onClick = onBackClick,
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = toolbarColor(),
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = toolbarColor())
                }
            }
        },
        actions = {
            if(isAddEmployeeButton) {
                IconButton(onClick = onAddEmployeeClick) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Employee")
                }
            }

            if(isSettingsEnabled) {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings")
                }
            }
        }
    )
}

@Composable
fun toolbarColor(): Color {
//    return if (ThemePreferenceManager.isDarkTheme.value) {
//        Color.White
//    } else {
//        Color.Black
//    }
    return Color.Black
}