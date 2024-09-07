package android.caged.employeemanagement.domain.usecases.appentry

import android.caged.employeemanagement.domain.manager.LocalUserManager

class DarkModeEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(isDarkMode: Boolean) {
        localUserManager.changeDarkMode(isDarkMode)
    }
}