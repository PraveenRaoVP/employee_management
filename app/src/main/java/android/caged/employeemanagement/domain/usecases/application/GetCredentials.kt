package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.CredentialsRepository

class GetCredentials(
    private val credentialsRepository : CredentialsRepository
) {
    suspend operator fun invoke(employeeId: Long) = credentialsRepository.getCredentials(employeeId)
}