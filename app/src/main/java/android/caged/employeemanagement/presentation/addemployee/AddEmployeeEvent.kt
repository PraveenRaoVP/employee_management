package android.caged.employeemanagement.presentation.addemployee

import android.caged.employeemanagement.domain.model.Position

sealed interface AddEmployeeEvent {
    data class EmployeeNameChanged(val name: String) : AddEmployeeEvent
    data class PositionChanged(val position: Position) : AddEmployeeEvent
    data class DesignationChanged(val designation: String) : AddEmployeeEvent
    data class SalaryChanged(val salary: String) : AddEmployeeEvent
    data class TeamChanged(val teamId: Long) : AddEmployeeEvent
    data class EmailChanged(val email: String) : AddEmployeeEvent
    data class PhoneChanged(val phone: String) : AddEmployeeEvent
    data class PhotoImageUrlChanged(val imageUrl: String) : AddEmployeeEvent
    data class TeamNameChanged(val teamName: String) : AddEmployeeEvent
    data class ChangeTeamState(val showCreateTeam: Boolean) : AddEmployeeEvent

    data object CreateTeam : AddEmployeeEvent
    data object CreateEmployee : AddEmployeeEvent
    data object ClearError : AddEmployeeEvent
}