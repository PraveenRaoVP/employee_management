package android.caged.employeemanagement.data.manager

import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.model.Employee
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "employee_credentials")

class LocalUserManagerImpl @Inject constructor(
    private val context: Context
) : LocalUserManager {

    companion object {
        private val EMPLOYEE_ID_KEY = longPreferencesKey("employee_id")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val IS_DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
    }

    override suspend fun saveCredentials(employeeId: Long, password: String) {
        context.dataStore.edit { preferences ->
            preferences[EMPLOYEE_ID_KEY] = employeeId
            preferences[PASSWORD_KEY] = password
        }
    }

    override suspend fun changeDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_KEY] = isDarkMode
        }
    }

    // Get credentials from DataStore
    override val credentials: Flow<Pair<Long?, String?>> = context.dataStore.data
        .map { preferences ->
            val employeeId = preferences[EMPLOYEE_ID_KEY]
            val password = preferences[PASSWORD_KEY]
            employeeId to password
        }

    override val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DARK_MODE_KEY] ?: false
        }

    // Clear credentials from DataStore
    override suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}