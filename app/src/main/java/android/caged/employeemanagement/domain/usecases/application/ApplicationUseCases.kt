package android.caged.employeemanagement.domain.usecases.application

data class ApplicationUseCases(
    val findEmployeeById: FindEmployeeById,
    val getCredentials : GetCredentials,
    val getEmployeeById : GetEmployeeById,
    val getTeamDetailsById: GetTeamDetailsById,
    val updateProfileImage: UpdateProfileImage,
    val getEmployeeCount : GetEmployeeCount,
    val getRecentEmployees: GetRecentEmployees,
    val getRecentEmployeesByTeamId: GetRecentEmployeesByTeamId,
    val searchEmployee: SearchEmployee,
    val searchEmployeeByTeam : SearchEmployeeByTeam,
    val getTeamCount: GetTeamCount,
    val getEmployeeCountByTeam : GetEmployeeCountByTeam,
    val getAllTeamsAsMap : GetAllTeamsAsMap,
    val getTeamByName: GetTeamByName,
    val getTeamById: GetTeamByID,
    val insertEmployee: InsertEmployee,
    val createTeam : CreateTeam,
    val updateTeamIDInEmployee : UpdateTeamIDInEmployee,
    val deleteEmployeeByID: DeleteEmployeeByID,
    val getAllEmployees: GetAllEmployees,
    val getEmployeesByTeamID : GetEmployeesByTeamID,
    val deleteTeam: DeleteTeam,
    val updatePassword: UpdatePassword,
    val insertCredentials: InsertCredentials,
    val deleteCredentials : DeleteCredentials
)