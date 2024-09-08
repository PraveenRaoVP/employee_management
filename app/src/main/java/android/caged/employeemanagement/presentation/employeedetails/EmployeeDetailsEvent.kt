package android.caged.employeemanagement.presentation.employeedetails

sealed interface EmployeeDetailsEvent {
    data class GetTeamName(val teamID : Long) : EmployeeDetailsEvent
    data class GetEmployee(val employeeID: Long) : EmployeeDetailsEvent
}