package android.caged.employeemanagement.presentation.navgraph

sealed class Screen(val route: String) {
    data object ChangePassword : Screen("change_password")

    data object AppStartNavigation : Screen("app_start_navigation")
    data object LoginRoute : Screen("login")
    data object PostLogin : Screen("post_login")
    data object AppNavigatorRoute : Screen("app_navigator")
    data object HomeRoute : Screen("home")
    data object ListingEmployees : Screen("listing_employees")
    data object EmployeeDetailsRoute : Screen("employee_details")
    data object SettingsRoute : Screen("settings")
    data object AddEmployeeRoute : Screen("add_employee")
    data object ProfileRoute : Screen("profile")
}