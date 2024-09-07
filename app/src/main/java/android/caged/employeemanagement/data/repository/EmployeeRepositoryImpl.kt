package android.caged.employeemanagement.data.repository

import android.caged.employeemanagement.data.local.EmployeeDao
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.repository.EmployeeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope

class EmployeeRepositoryImpl(
    private val employeeDao: EmployeeDao
) : EmployeeRepository {

    override suspend fun getEmployeeById(employeeId: Long): Employee? {
        return employeeDao.getEmployeeById(employeeId)
    }

    override suspend fun insertEmployee(employee: Employee) {
        employeeDao.insertEmployee(employee)
    }

    override suspend fun getEmployeesByTeamName(teamName: String): List<Employee> {
        return employeeDao.getEmployeesByTeamName(teamName)
    }

    override suspend fun searchEmployeesByName(searchQuery: String): List<Employee> {
        return employeeDao.searchEmployeesByName(searchQuery)
    }

    override suspend fun getAllEmployees(): List<Employee> {
        return employeeDao.getAllEmployees()
    }

    override suspend fun deleteEmployeeById(employeeId: Long) {
        employeeDao.deleteEmployeeById(employeeId)
    }

    override suspend fun updateProfileImageUrl(employeeId: Long, profileImageUrl: String) {
        employeeDao.updateProfileImageUrl(employeeId, profileImageUrl)
    }
}