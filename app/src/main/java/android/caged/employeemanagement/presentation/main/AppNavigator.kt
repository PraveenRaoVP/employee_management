package android.caged.employeemanagement.presentation.main

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.presentation.addemployee.AddEmployeeScreen
import android.caged.employeemanagement.presentation.components.ActionBar
import android.caged.employeemanagement.presentation.components.BottomNavBar
import android.caged.employeemanagement.presentation.employeedetails.EmployeeDetailsEvent
import android.caged.employeemanagement.presentation.employeedetails.EmployeeDetailsScreen
import android.caged.employeemanagement.presentation.employeedetails.EmployeeDetailsViewModel
import android.caged.employeemanagement.presentation.home.GraphScreen
import android.caged.employeemanagement.presentation.home.HomeScreen
import android.caged.employeemanagement.presentation.home.HomeViewModel
import android.caged.employeemanagement.presentation.listingemployees.ListingEmployeesScreen
import android.caged.employeemanagement.presentation.listingemployees.ListingEmployeesViewModel
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.caged.employeemanagement.presentation.profilepage.ProfilePageEvent
import android.caged.employeemanagement.presentation.profilepage.ProfilePageScreen
import android.caged.employeemanagement.presentation.profilepage.ProfilePageViewModel
import android.caged.employeemanagement.presentation.settings.SettingsScreen
import android.caged.employeemanagement.presentation.settings.SettingsViewModel
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
                label = "Employee List"
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

    var selectedItemEmp by rememberSaveable {
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

    selectedItemEmp = when(backStackState?.destination?.route) {
        Screen.HomeRoute.route -> 0
        Screen.ProfileRoute.route -> 1
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
                        bottomNavEmployeeItems[selectedItemEmp].label
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
                    selectedItem = if(appNavigatorViewModel.userType.value == Position.ADMIN || appNavigatorViewModel.userType.value == Position.MANAGER) {
                        selectedItem
                    } else {
                        selectedItemEmp
                    },
                    onItemSelect = { index ->
                        if(appNavigatorViewModel.userType.value == Position.ADMIN || appNavigatorViewModel.userType.value == Position.MANAGER) {
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
                                else -> {
                                    Log.i("AppNavigator", "Invalid index")
                                }
                            }
                        } else {
                            when (index) {
                                0 -> {
                                    appNavigatorViewModel.previousScreen.value = Screen.HomeRoute.route
                                    navigateToTab(navController, Screen.HomeRoute.route)
                                }

                                1 -> {
                                    appNavigatorViewModel.previousScreen.value = Screen.ProfileRoute.route
                                    navigateToTab(navController, Screen.ProfileRoute.route)
                                }
                                else -> {
                                    Log.i("AppNavigator", "Invalid index")
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        val bottomPadding = innerPadding.calculateBottomPadding()
        val topPadding = innerPadding.calculateTopPadding()
        NavHost(
            navController = navController,
            startDestination = Screen.HomeRoute.route,
            modifier = Modifier.padding(bottom = bottomPadding, top = topPadding)
        ) {
            composable(route = Screen.HomeRoute.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val state by homeViewModel.uiState.collectAsState()
                HomeScreen(
                    navigateToPopUp = navigateToPopUp,
                    currentUser = appNavigatorViewModel.currentUser.value,
                    state = state,
                    navigate = { employee -> navigateToDetails(navController, employee.employeeId) },
                    onRefetchData = {
                        homeViewModel.refreshData()
                    }
                )
            }
            


            composable(route = Screen.SettingsRoute.route) {
                val viewModel: SettingsViewModel = hiltViewModel()
                SettingsScreen(
                    navigatePopUp = navigateToPopUp,
                    navigatePopBackStack = { navigatePopBackStack(navController) } ,
                    signOut = viewModel::signOut
                )
            }


            composable(route = Screen.ListingEmployees.route) {
                val viewModel: ListingEmployeesViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsState()
                ListingEmployeesScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    navigate = { employee -> navigateToDetails(navController, employee.employeeId) },
                    shouldDelete = viewModel.shouldDelete.value,
                )
            }

            composable(route = Screen.ProfileRoute.route) {
                val viewModel: ProfilePageViewModel = hiltViewModel()
                val homeViewModel: HomeViewModel = hiltViewModel()

                val employeeID = appNavigatorViewModel.currentUser.value.employeeId

                employeeID.let {
                    viewModel.onEvent(ProfilePageEvent.SetUserId(it))
                }

                viewModel.employee.value?.let { it1 ->
                    ProfilePageScreen(
                        user = it1,
                        teamName = viewModel.teamName.value,
                        onEvent = viewModel::onEvent,
                        state = viewModel.uiState.value,
//                        refreshData = { homeViewModel.refreshData() }
                    )
                }
            }

            composable(route = Screen.EmployeeDetailsRoute.route) {
                val viewModel: EmployeeDetailsViewModel = hiltViewModel()
                val employeeId = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("employeeID")
                employeeId?.let {
                    viewModel.onEvent(EmployeeDetailsEvent.GetEmployee(it))
                }

                EmployeeDetailsScreen(
                    employee = viewModel.employee.value,
                    teamName = viewModel.teamName.value,
                    onBack = {
                        navigatePopBackStack(navController)
                    },
                    onEvent = viewModel::onEvent
                )
            }

            composable(route = Screen.AddEmployeeRoute.route) {
//                AddEmployeeScreen(state = , teamState = , teams = , onEvent = , navigateToPopUp = navigateToPopUp)
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

fun navigatePopBackStack(navController: NavController) {
    navController.popBackStack()
}

private fun navigateToDetails(navController: NavController, employeeID: Long) {
    navController.currentBackStackEntry?.savedStateHandle?.set("employeeID", employeeID)
    navController.navigate(Screen.EmployeeDetailsRoute.route)
}