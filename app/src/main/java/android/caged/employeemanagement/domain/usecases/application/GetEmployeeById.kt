package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class GetEmployeeById(
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke(employeeId: Long) = employeeRepository.getEmployeeById(employeeId)
}