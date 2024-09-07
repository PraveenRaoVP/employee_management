package android.caged.employeemanagement.presentation.navgraph

import android.caged.employeemanagement.presentation.addemployee.AddEmployeeScreen
import android.caged.employeemanagement.presentation.auth.login.LoginScreen
import android.caged.employeemanagement.presentation.auth.login.LoginViewModel
import android.caged.employeemanagement.presentation.main.AppNavigator
import android.caged.employeemanagement.presentation.main.AppNavigatorViewModel
import android.caged.employeemanagement.presentation.settings.SettingsScreen
import android.caged.employeemanagement.presentation.settings.SettingsViewModel
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Screen.AppStartNavigation.route,
            startDestination = Screen.LoginRoute.route
        ) {
            composable(route = Screen.LoginRoute.route) {
                val viewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    onEvent = viewModel::onEvent,
                    state = viewModel.state.value,
                    navigateToPopUp = { route, popUp ->
                        navigateToPopUp(
                            route,
                            popUp,
                            navController
                        )
                    }
                )
            }
        }

        navigation(
            route = Screen.PostLogin.route,
            startDestination = Screen.AppNavigatorRoute.route
        ) {
            composable(route = Screen.AppNavigatorRoute.route) {
                val viewModel: AppNavigatorViewModel = hiltViewModel()
                AppNavigator(
                    appNavigatorViewModel = viewModel,
                    navigateTo = { route -> navigateTo(route, navController) },
                    navigateToPopUp = { route, popUp ->
                        navigateToPopUp(
                            route,
                            popUp,
                            navController
                        )
                    }
                )
            }
            composable(route = Screen.SettingsRoute.route) {
                val viewModel: SettingsViewModel = hiltViewModel()
                SettingsScreen(navigatePopUp = { route, popUp ->
                    navigateToPopUp(
                        route,
                        popUp,
                        navController
                    )
                }, signOut = viewModel::signOut)
            }

            composable(route = Screen.AddEmployeeRoute.route) {
                AddEmployeeScreen()
            }
        }
    }
}

fun navigateTo(route: String, navController: NavHostController) {
    navController.navigate(route)
}

fun navigateToPopUp(route: String, popUp: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(popUp) {
            inclusive = true
        }
    }
}