package android.caged.employeemanagement.presentation.changepassword

sealed interface ChangePasswordEvent {
    data class OldPasswordChanged(val oldPassword: String) : ChangePasswordEvent
    data class NewPasswordChanged(val newPassword: String) : ChangePasswordEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : ChangePasswordEvent
    object ChangePassword : ChangePasswordEvent
}