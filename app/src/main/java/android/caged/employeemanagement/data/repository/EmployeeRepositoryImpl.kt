package android.caged.employeemanagement.data.repository

import android.caged.employeemanagement.data.local.EmployeeDao
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.repository.EmployeeRepository
import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class EmployeeRepositoryImpl(
    private val employeeDao: EmployeeDao
) : EmployeeRepository {

    override fun getEmployeeById(employeeId: Long): Flow<Employee?> {
        return employeeDao.getEmployeeById(employeeId)
    }

    override suspend fun insertEmployee(employee: Employee) : Long {
        return employeeDao.insertEmployee(employee)
    }

    override fun getEmployeesByTeamName(teamName: String): Flow<List<Employee>> {
        return employeeDao.getEmployeesByTeamName(teamName)
    }

    override fun getEmployeesByTeamId(teamID: Long): Flow<List<Employee>> {
        return employeeDao.getEmployeesByTeamName(teamID)
    }

    override fun searchEmployeesByName(searchQuery: String): Flow<List<Employee>> {
        return employeeDao.searchEmployeesByName(searchQuery)
    }

    override fun getAllEmployees(): Flow<List<Employee>> {
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

    override fun getRecentEmployees(): Flow<List<Employee>> {
        return employeeDao.getRecentEmployees()
    }

    override fun getRecentEmployeesByTeamId(teamId: Long): Flow<List<Employee>> {
        return employeeDao.getRecentEmployeesByTeamId(teamId)
    }

    override fun searchEmployee(searchQuery: String): Flow<List<Employee>> {
        return employeeDao.searchEmployeesByName(searchQuery)
    }

    override fun searchEmployeeByTeam(searchQuery: String, teamID: Long): Flow<List<Employee>> {
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