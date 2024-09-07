package android.caged.employeemanagement.presentation.auth.login

data class LoginState(
    val employeeId: String = "",
    val isErrorEmpId: Boolean = false,
    val isErrorPassword: Boolean = false,
    val errorEmpId: String = "",
    val errorPassword: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
