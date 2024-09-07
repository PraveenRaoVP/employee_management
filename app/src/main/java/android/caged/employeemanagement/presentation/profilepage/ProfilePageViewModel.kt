package android.caged.employeemanagement.presentation.profilepage

import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.domain.usecases.application.GetTeamDetailsById
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases
) : ViewModel() {

    val uiState = mutableStateOf(ProfilePageState())
    val userId = mutableStateOf(-1L)

    fun onEvent(event: ProfilePageEvent) {
        when (event) {
            is ProfilePageEvent.UpdateProfileImageUrl -> {
                viewModelScope.launch {
                    applicationUseCases.updateProfileImage(userId.value,event.newProfileImageUrl)
                    uiState.value = uiState.value.copy(profileImageUrl = event.newProfileImageUrl)
                }
            }

            is ProfilePageEvent.SetUserId -> {
                userId.value = event.userId
            }

            is ProfilePageEvent.SetProfileImageUrl -> {
                uiState.value = uiState.value.copy(profileImageUrl = event.profileImageUrl)
            }
        }
    }
}