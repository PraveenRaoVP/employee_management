package android.caged.employeemanagement.presentation.employeedetails

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val applicationUseCases: ApplicationUseCases
) : ViewModel() {

    private val _employee: MutableStateFlow<Employee?> = MutableStateFlow(null)
    val employee: StateFlow<Employee?> get() = _employee

    val teamName = mutableStateOf("")

    fun onEvent(event: EmployeeDetailsEvent) {
        when(event) {
            is EmployeeDetailsEvent.GetTeamName -> {
                viewModelScope.launch {
                    teamName.value = applicationUseCases.teamUseCases.getTeamByID(event.teamID)?.teamName ?: "No team"
                }
            }
            is EmployeeDetailsEvent.GetEmployee -> {
                getEmployee(event.employeeID)
            }
        }
    }

    private fun getEmployee(employeeId: Long) {
        viewModelScope.launch {
            applicationUseCases.employeeUseCases.getEmployeeById(employeeId).collect {
                _employee.value = it
            }
        }
    }
}