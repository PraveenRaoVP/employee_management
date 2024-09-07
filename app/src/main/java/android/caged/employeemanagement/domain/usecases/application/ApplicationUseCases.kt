package android.caged.employeemanagement.domain.usecases.application

data class ApplicationUseCases(
    val findEmployeeById: FindEmployeeById,
    val getCredentials : GetCredentials,
    val getEmployeeById : GetEmployeeById,
    val getTeamDetailsById: GetTeamDetailsById,
    val updateProfileImage: UpdateProfileImage
)