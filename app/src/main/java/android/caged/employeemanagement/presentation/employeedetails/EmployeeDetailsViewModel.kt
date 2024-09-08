package android.caged.employeemanagement.presentation.employeedetails

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases
) : ViewModel() {

    val employee = mutableStateOf<Employee?>(null)
    val teamName = mutableStateOf("")

    fun onEvent(event: EmployeeDetailsEvent) {
        when(event) {
            is EmployeeDetailsEvent.GetTeamName -> {
                viewModelScope.launch {
                    teamName.value = applicationUseCases.getTeamById(event.teamID)?.teamName ?: "No team"
                }
            }
            is EmployeeDetailsEvent.GetEmployee -> {
                getEmployee(event.employeeID)
            }
        }
    }

    private fun getEmployee(employeeId: Long) {
        viewModelScope.launch {
            val fetchedEmployee = applicationUseCases.getEmployeeById(employeeId)
            employee.value = fetchedEmployee
        }
    }
}