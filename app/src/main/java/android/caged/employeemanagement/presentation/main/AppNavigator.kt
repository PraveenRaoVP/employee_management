package android.caged.employeemanagement.presentation.main

import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.presentation.components.ActionBar
import android.caged.employeemanagement.presentation.components.BottomNavBar
import android.caged.employeemanagement.presentation.home.HomeScreen
import android.caged.employeemanagement.presentation.home.HomeViewModel
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.caged.employeemanagement.presentation.profilepage.ProfilePageScreen
import android.caged.employeemanagement.presentation.profilepage.ProfilePageViewModel
import android.caged.employeemanagement.presentation.settings.SettingsScreen
import android.caged.employeemanagement.presentation.settings.SettingsViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigator(
    appNavigatorViewModel: AppNavigatorViewModel,
    navigateTo: (String) -> Unit,
    navigateToPopUp: (String, String) -> Unit
) {

    val bottomNavAdminItems = remember {
        listOf(
            BottomNavigationItem(
                route = Screen.HomeRoute.route,
                icon = Icons.Default.Home,
                label = "Home"
            ),
            BottomNavigationItem(
                route = Screen.ListingEmployees.route,
                icon = Icons.AutoMirrored.Filled.List,
                label = "Listing Employees"
            ),
            BottomNavigationItem(
                route = Screen.ProfileRoute.route,
                icon = Icons.Default.Person,
                label = "Profile"
            )
        )
    }

    val bottomNavEmployeeItems = remember {
        listOf(
            BottomNavigationItem(
                route = Screen.HomeRoute.route,
                icon = Icons.Default.Home,
                label = "Home"
            ),
            BottomNavigationItem(
                route = Screen.ProfileRoute.route,
                icon = Icons.Default.Person,
                label = "Profile"
            )
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screen.HomeRoute.route ||
                backStackState?.destination?.route == Screen.ListingEmployees.route ||
                backStackState?.destination?.route == Screen.ProfileRoute.route
    }

    selectedItem = when (backStackState?.destination?.route) {
        Screen.HomeRoute.route -> 0
        Screen.ListingEmployees.route -> 1
        Screen.ProfileRoute.route -> 2
        else -> 0
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            if (backStackState?.destination?.route != Screen.SettingsRoute.route
            ) {
                ActionBar(
                    title = if(appNavigatorViewModel.userType.value == Position.ADMIN || appNavigatorViewModel.userType.value == Position.MANAGER) {
                        bottomNavAdminItems[selectedItem].label
                    } else {
                        bottomNavEmployeeItems[selectedItem].label
                    },
                    onSettingsClick = {
                        navigateTo(Screen.SettingsRoute.route)
                    },
                    isAddEmployeeButton = if(appNavigatorViewModel.userType.value == Position.ADMIN &&
                            backStackState?.destination?.route == Screen.ListingEmployees.route) {
                        true
                    } else {
                        false
                    },
                    onAddEmployeeClick = {
                        navigateTo(Screen.AddEmployeeRoute.route)
                    }
                )
            }
        },
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavBar(
                    items = if(appNavigatorViewModel.userType.value == Position.ADMIN || appNavigatorViewModel.userType.value == Position.MANAGER) {
                        bottomNavAdminItems
                    } else {
                        bottomNavEmployeeItems
                    },
                    selectedItem = selectedItem,
                    onItemSelect = { index ->
                        when (index) {
                            0 -> {
                                appNavigatorViewModel.previousScreen.value = Screen.HomeRoute.route
                                navigateToTab(navController, Screen.HomeRoute.route)
                            }

                            1 -> {
                                appNavigatorViewModel.previousScreen.value = Screen.ListingEmployees.route
                                navigateToTab(navController, Screen.ListingEmployees.route)
                            }

                            2 -> {
                                appNavigatorViewModel.previousScreen.value = Screen.ProfileRoute.route
                                navigateToTab(navController, Screen.ProfileRoute.route)
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        val bottomPadding = innerPadding.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Screen.HomeRoute.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Screen.HomeRoute.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    navigateToPopUp = navigateToPopUp,
                    navigateTo = navigateTo,
                    currentUser = appNavigatorViewModel.currentUser.value
                )
            }

            composable(route = Screen.SettingsRoute.route) {
                val viewModel: SettingsViewModel = hiltViewModel()
                SettingsScreen(
                    navigatePopUp = navigateToPopUp,
//                    previousScreen = appNavigatorViewModel.previousScreen.value,
                    signOut = viewModel::signOut
                )
            }


            composable(route = Screen.ListingEmployees.route) {
                // TODO: Implement Listing employees screen
            }

            composable(route = Screen.ProfileRoute.route) {
                val viewModel: ProfilePageViewModel = hiltViewModel()
                ProfilePageScreen(
                    user = appNavigatorViewModel.currentUser.value,
                    teamName = appNavigatorViewModel.currentUserTeam.value.teamName,
                    onEvent = viewModel::onEvent,
                    state = viewModel.uiState.value
                )
            }

            composable(route = Screen.EmployeeDetailsRoute.route) {
                // TODO: Employee Details Screen
            }

            composable(route = Screen.AddEmployeeRoute.route) {

            }
        }
    }
}

data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigatePopBackStack(navController: NavController) {
    navController.popBackStack()
}

private fun navigateToDetails(navController: NavController) {
//    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
//    navController.navigate(Route.DetailsScreen.route)
}