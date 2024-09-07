package android.caged.employeemanagement.domain.usecases.appentry

import android.caged.employeemanagement.data.manager.LocalUserManagerImpl
import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.utils.returnHashedPassword

class SaveCredentialsEntry(
    private val localUserManager : LocalUserManager
) {
    suspend operator fun invoke(employeeId: Long, password: String) {
        localUserManager.saveCredentials(employeeId, returnHashedPassword(password))
    }
}