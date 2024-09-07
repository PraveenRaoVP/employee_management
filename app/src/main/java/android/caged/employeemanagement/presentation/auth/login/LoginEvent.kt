package android.caged.employeemanagement.presentation.auth.login

sealed interface LoginEvent {
    data object OnLoginClick : LoginEvent
    data class EmployeeIDChanged(val employeeID: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object ClearErrors : LoginEvent
}