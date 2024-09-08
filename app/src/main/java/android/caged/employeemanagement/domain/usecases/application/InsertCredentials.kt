package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Credentials
import android.caged.employeemanagement.domain.repository.CredentialsRepository

class InsertCredentials(
    private val credentialsRepository : CredentialsRepository
) {
    suspend operator fun invoke(employeeId: Long, password: String) {
        credentialsRepository.insertCredentials(Credentials(employeeID = employeeId, password = password))
    }
}