package android.caged.employeemanagement.presentation.changepassword

data class ChangePasswordState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val error: String = ""
)
