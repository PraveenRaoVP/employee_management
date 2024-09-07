package android.caged.employeemanagement.presentation.profilepage

interface ProfilePageEvent {
    data class UpdateProfileImageUrl(val newProfileImageUrl: String) : ProfilePageEvent
    data class SetUserId(val userId: Long) : ProfilePageEvent
    data class SetProfileImageUrl(val profileImageUrl: String) : ProfilePageEvent
}