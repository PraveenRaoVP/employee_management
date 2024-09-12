package android.caged.employeemanagement.domain.usecases.application

data class ApplicationUseCases(
    val employeeUseCases: EmployeeUseCases,
    val teamUseCases: TeamUseCases,
    val credentialUseCases: CredentialUseCases
)