package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class UpdateProfileImage(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(employeeId: Long, profileImageUrl: String) {
        employeeRepository.updateProfileImageUrl(employeeId, profileImageUrl)
    }
}