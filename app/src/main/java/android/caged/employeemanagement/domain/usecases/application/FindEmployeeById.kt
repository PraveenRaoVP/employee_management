package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class FindEmployeeById(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(employeeId: Long) = employeeRepository.getEmployeeById(employeeId)
}