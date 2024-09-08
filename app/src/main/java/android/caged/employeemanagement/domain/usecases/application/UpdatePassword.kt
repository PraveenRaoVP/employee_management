package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.CredentialsRepository

class UpdatePassword(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(employeeId: Long, password: String) {
        credentialsRepository.updatePassword(employeeId, password)
    }
}