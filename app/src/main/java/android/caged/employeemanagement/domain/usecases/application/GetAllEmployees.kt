package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.repository.EmployeeRepository

class GetAllEmployees(
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke() = employeeRepository.getAllEmployees()
}