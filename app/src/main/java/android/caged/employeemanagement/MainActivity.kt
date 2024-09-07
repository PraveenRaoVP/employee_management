package android.caged.employeemanagement

import android.Manifest
import android.caged.employeemanagement.data.repository.EmployeeRepositoryImpl
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.presentation.navgraph.NavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.caged.employeemanagement.ui.theme.EmployeeManagementTheme
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Check and request permissions at the start
        checkAndRequestPermissions()

        setContent {
            EmployeeManagementTheme {
                val startDestination = mainViewModel.startDestination
                NavGraph(startDestination = startDestination)
            }
        }
    }

    // Function to check and request permissions
    private fun checkAndRequestPermissions() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted
            }
            shouldShowRequestPermissionRationale(permission) -> {
                // Show an explanation to the user
                requestPermissionLauncher.launch(permission)
            }
            else -> {
                // Directly request the permission
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    // Launcher to handle the permission request result
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted
            // You can proceed with your app's functionality that requires the permission
        } else {
            // Permission is denied
            // You can show a message to the user or handle the denial appropriately
        }
    }
}
