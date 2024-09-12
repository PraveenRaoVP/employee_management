package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Credentials
import android.caged.employeemanagement.domain.repository.CredentialsRepository

class CredentialUseCases(
    private val credentialsRepository: CredentialsRepository
) {
    suspend fun getCredentials(employeeID: Long) = credentialsRepository.getCredentials(employeeID)

    suspend fun insertCredentials(employeeID: Long, password: String) = credentialsRepository.insertCredentials(Credentials(employeeID = employeeID, password =  password))

    suspend fun deleteCredentials(employeeID: Long) = credentialsRepository.deleteCredentials(employeeID)

    suspend fun updatePassword(employeeID: Long, password: String) = credentialsRepository.updatePassword(employeeID, password)
}