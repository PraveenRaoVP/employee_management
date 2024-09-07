package android.caged.employeemanagement.domain.manager

import android.caged.employeemanagement.domain.model.Employee
import kotlinx.coroutines.flow.Flow


interface LocalUserManager {
        suspend fun saveCredentials(employeeId: Long, password: String) // save the app entry i.e if the user is logging in with their credentials
        val credentials: Flow<Pair<Long?, String?>>
        val isDarkMode: Flow<Boolean>
        suspend fun clearCredentials() // read the app entry value from the DataStore
        suspend fun changeDarkMode(isDarkMode: Boolean)
}