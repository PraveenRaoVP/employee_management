package android.caged.employeemanagement.data.repository

import android.caged.employeemanagement.data.local.EmployeeDao
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.repository.EmployeeRepository
import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope

class EmployeeRepositoryImpl(
    private val employeeDao: EmployeeDao
) : EmployeeRepository {

    override suspend fun getEmployeeById(employeeId: Long): Employee? {
        return employeeDao.getEmployeeById(employeeId)
    }

    override suspend fun insertEmployee(employee: Employee) : Long {
        return employeeDao.insertEmployee(employee)
    }

    override suspend fun getEmployeesByTeamName(teamName: String): List<Employee> {
        return employeeDao.getEmployeesByTeamName(teamName)
    }

    override suspend fun getEmployeesByTeamId(teamID: Long): List<Employee> {
        return employeeDao.getEmployeesByTeamName(teamID)
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

    override suspend fun getEmployeeCount(): Int {
        return employeeDao.getEmployeeCount()
    }

    override suspend fun getRecentEmployees(): List<Employee> {
        return employeeDao.getRecentEmployees()
    }

    override suspend fun getRecentEmployeesByTeamId(teamId: Long): List<Employee> {
        return employeeDao.getRecentEmployeesByTeamId(teamId)
    }

    override suspend fun searchEmployee(searchQuery: String): List<Employee> {
        return employeeDao.searchEmployeesByName(searchQuery)
    }

    override suspend fun searchEmployeeByTeam(searchQuery: String, teamID: Long): List<Employee> {
        return employeeDao.searchEmployeeByTeam(searchQuery, teamID)
    }

    override suspend fun getEmployeeCountByTeamID(teamId: Long) : Int {
        return employeeDao.getEmployeeCountByTeamID(teamId)
    }

    override suspend fun updateEmployeeTeam(employeeId: Long, teamID: Long) {
        Log.i("EmployeeRepositoryImpl", "updateEmployeeTeam: $employeeId, $teamID")
        employeeDao.updateEmployeeTeam(teamID, employeeId)
    }
}