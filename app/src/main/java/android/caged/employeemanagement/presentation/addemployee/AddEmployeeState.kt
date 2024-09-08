package android.caged.employeemanagement.presentation.addemployee

import android.caged.employeemanagement.domain.model.Position

data class AddEmployeeState(
    val employeeName: String = "",
    val position: Position = Position.ADMIN,
    val designation: String = "",
    val salary: String = "0.0",
    val teamId: Long = -1L,
    val email: String = "",
    val phone: String = "",
    val profileImageUrl: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)
