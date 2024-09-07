package android.caged.employeemanagement.data.repository

import android.caged.employeemanagement.data.local.CredentialsDao
import android.caged.employeemanagement.domain.model.Credentials
import android.caged.employeemanagement.domain.repository.CredentialsRepository

class CredentialsRepositoryImpl(
    private val credentialsDao: CredentialsDao
) : CredentialsRepository {
    override suspend fun getCredentials(employeeId: Long): Credentials? {
        return credentialsDao.getCredentialsByEmployeeId(employeeId)
    }

    override suspend fun insertCredentials(credentials: Credentials) {
        credentialsDao.insertCredentials(credentials)
    }

    override suspend fun updatePassword(employeeId: Long, password: String) {
        credentialsDao.updatePassword(employeeId, password)
    }
}