package android.caged.employeemanagement.domain.repository

import android.caged.employeemanagement.domain.model.Credentials


interface CredentialsRepository {
    suspend fun getCredentials(employeeId: Long): Credentials?
    suspend fun insertCredentials(credentials: Credentials)
    suspend fun updatePassword(employeeId: Long, password: String)
}