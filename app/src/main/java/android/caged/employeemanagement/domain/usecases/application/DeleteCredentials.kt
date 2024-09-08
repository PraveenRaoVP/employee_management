package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.CredentialsRepository

class DeleteCredentials(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(employeeId: Long) {
        credentialsRepository.deleteCredentials(employeeId)
    }
}