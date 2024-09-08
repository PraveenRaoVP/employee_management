package android.caged.employeemanagement.domain.usecases.application

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.repository.EmployeeRepository

class InsertEmployee(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(
        employee: Employee
    ) : Long {
        return employeeRepository.insertEmployee(employee)
    }
}