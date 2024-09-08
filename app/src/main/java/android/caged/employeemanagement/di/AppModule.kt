package android.caged.employeemanagement.di

import android.app.Application
import android.caged.employeemanagement.data.local.CredentialsDao
import android.caged.employeemanagement.data.local.EmployeeDao
import android.caged.employeemanagement.data.local.EmployeeManagementDatabase
import android.caged.employeemanagement.data.local.PositionTypeConverter
import android.caged.employeemanagement.data.local.TeamDao
import android.caged.employeemanagement.data.manager.LocalUserManagerImpl
import android.caged.employeemanagement.data.repository.CredentialsRepositoryImpl
import android.caged.employeemanagement.data.repository.EmployeeRepositoryImpl
import android.caged.employeemanagement.data.repository.TeamRepositoryImpl
import android.caged.employeemanagement.domain.manager.LocalUserManager
import android.caged.employeemanagement.domain.repository.CredentialsRepository
import android.caged.employeemanagement.domain.repository.EmployeeRepository
import android.caged.employeemanagement.domain.repository.TeamRepository
import android.caged.employeemanagement.domain.usecases.appentry.DarkModeEntry
import android.caged.employeemanagement.domain.usecases.appentry.SaveCredentialsEntry
import android.caged.employeemanagement.domain.usecases.application.ApplicationUseCases
import android.caged.employeemanagement.domain.usecases.application.CreateTeam
import android.caged.employeemanagement.domain.usecases.application.DeleteEmployeeByID
import android.caged.employeemanagement.domain.usecases.application.DeleteTeam
import android.caged.employeemanagement.domain.usecases.application.FindEmployeeById
import android.caged.employeemanagement.domain.usecases.application.GetAllEmployees
import android.caged.employeemanagement.domain.usecases.application.GetAllTeamsAsMap
import android.caged.employeemanagement.domain.usecases.application.GetCredentials
import android.caged.employeemanagement.domain.usecases.application.GetEmployeeById
import android.caged.employeemanagement.domain.usecases.application.GetEmployeeCount
import android.caged.employeemanagement.domain.usecases.application.GetEmployeeCountByTeam
import android.caged.employeemanagement.domain.usecases.application.GetEmployeesByTeamID
import android.caged.employeemanagement.domain.usecases.application.GetRecentEmployees
import android.caged.employeemanagement.domain.usecases.application.GetRecentEmployeesByTeamId
import android.caged.employeemanagement.domain.usecases.application.GetTeamByID
import android.caged.employeemanagement.domain.usecases.application.GetTeamByName
import android.caged.employeemanagement.domain.usecases.application.GetTeamCount
import android.caged.employeemanagement.domain.usecases.application.GetTeamDetailsById
import android.caged.employeemanagement.domain.usecases.application.InsertEmployee
import android.caged.employeemanagement.domain.usecases.application.SearchEmployee
import android.caged.employeemanagement.domain.usecases.application.SearchEmployeeByTeam
import android.caged.employeemanagement.domain.usecases.application.UpdateProfileImage
import android.caged.employeemanagement.domain.usecases.application.UpdateTeamIDInEmployee
import android.content.Context
import android.util.Log
import androidx.room.Delete
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /********************************************************************************************************************
     * Application
     ********************************************************************************************************************/

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager {
        return LocalUserManagerImpl(application)
    }

    /********************************************************************************************************************
     * Use Cases
     ********************************************************************************************************************/

    @Provides
    @Singleton
    fun provideApplicationUseCases(
        findEmployeeById: FindEmployeeById,
        getCredentials: GetCredentials,
        getEmployeeById: GetEmployeeById,
        getTeamDetailsById: GetTeamDetailsById,
        updateProfileImage: UpdateProfileImage,
        getEmployeeCount: GetEmployeeCount,
        getRecentEmployees: GetRecentEmployees,
        getRecentEmployeesByTeamId: GetRecentEmployeesByTeamId,
        searchEmployee: SearchEmployee,
        searchEmployeeByTeam: SearchEmployeeByTeam,
        getTeamCount: GetTeamCount,
        getEmployeeCountByTeam : GetEmployeeCountByTeam,
        getAllTeamsAsMap: GetAllTeamsAsMap,
        getTeamByName: GetTeamByName,
        getTeamByID: GetTeamByID,
        insertEmployee: InsertEmployee,
        createTeam: CreateTeam,
        updateTeamIDInEmployee: UpdateTeamIDInEmployee,
        deleteEmployeeByID : DeleteEmployeeByID,
        getAllEmployees: GetAllEmployees,
        getEmployeesByTeamID: GetEmployeesByTeamID,
        deleteTeam: DeleteTeam
    ): ApplicationUseCases {
        return ApplicationUseCases(
            findEmployeeById = findEmployeeById,
            getCredentials = getCredentials,
            getEmployeeById = getEmployeeById,
            getTeamDetailsById = getTeamDetailsById,
            updateProfileImage = updateProfileImage,
            getEmployeeCount = getEmployeeCount,
            getRecentEmployees = getRecentEmployees,
            getRecentEmployeesByTeamId = getRecentEmployeesByTeamId,
            searchEmployee = searchEmployee,
            searchEmployeeByTeam = searchEmployeeByTeam,
            getTeamCount = getTeamCount,
            getEmployeeCountByTeam = getEmployeeCountByTeam,
            getAllTeamsAsMap = getAllTeamsAsMap,
            getTeamByName = getTeamByName,
            getTeamById = getTeamByID,
            insertEmployee = insertEmployee,
            createTeam = createTeam,
            updateTeamIDInEmployee = updateTeamIDInEmployee,
            deleteEmployeeByID = deleteEmployeeByID,
            getAllEmployees = getAllEmployees,
            getEmployeesByTeamID = getEmployeesByTeamID,
            deleteTeam = deleteTeam
        )
    }

    @Provides
    @Singleton
    fun provideSaveCredentialsEntry(
        localUserManager: LocalUserManager
    ): SaveCredentialsEntry {
        return SaveCredentialsEntry(localUserManager)
    }

    @Provides
    @Singleton
    fun provideDarkModeEntry(
        localUserManager: LocalUserManager
    ): DarkModeEntry {
        return DarkModeEntry(localUserManager)
    }

    @Provides
    @Singleton
    fun provideFindEmployeeById(
        employeeRepository: EmployeeRepository
    ): FindEmployeeById {
        return FindEmployeeById(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetCredentials(
        credentialsRepository: CredentialsRepository
    ): GetCredentials {
        return GetCredentials(credentialsRepository)
    }

    @Provides
    @Singleton
    fun provideGetEmployeeById(
        employeeRepository: EmployeeRepository
    ): GetEmployeeById {
        return GetEmployeeById(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetTeamDetailsById(
        teamRepository: TeamRepository
    ): GetTeamDetailsById {
        return GetTeamDetailsById(teamRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllTeamsAsMap(
        teamRepository: TeamRepository
    ) : GetAllTeamsAsMap {
        return GetAllTeamsAsMap(teamRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileImage(
        employeeRepository: EmployeeRepository
    ): UpdateProfileImage {
        return UpdateProfileImage(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetEmployeeCount(
        employeeRepository: EmployeeRepository
    ): GetEmployeeCount {
        return GetEmployeeCount(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetEmployeeCountByTeamId(
        employeeRepository: EmployeeRepository
    ): GetEmployeeCountByTeam {
        return GetEmployeeCountByTeam(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetRecentEmployees(
        employeeRepository: EmployeeRepository
    ): GetRecentEmployees {
        return GetRecentEmployees(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetRecentEmployeesByTeamId(
        employeeRepository: EmployeeRepository
    ): GetRecentEmployeesByTeamId {
        return GetRecentEmployeesByTeamId(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideSearchEmployee(
        employeeRepository: EmployeeRepository
    ): SearchEmployee {
        return SearchEmployee(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideSearchEmployeeByTeam(
        employeeRepository: EmployeeRepository
    ): SearchEmployeeByTeam {
        return SearchEmployeeByTeam(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetTeamCount(
        teamRepository: TeamRepository
    ) : GetTeamCount {
        return GetTeamCount(teamRepository)
    }

    @Provides
    @Singleton
    fun provideGetTeamByName(
        teamRepository: TeamRepository
    ) : GetTeamByName {
        return GetTeamByName(teamRepository)
    }

    @Provides
    @Singleton
    fun provideGetTeamById(
        teamRepository: TeamRepository
    ): GetTeamByID {
        return GetTeamByID(teamRepository)
    }

    @Provides
    @Singleton
    fun provideInsertEmployee(
        employeeRepository: EmployeeRepository
    ): InsertEmployee {
        return InsertEmployee(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideCreateTeam(
        teamRepository: TeamRepository
    ): CreateTeam {
        return CreateTeam(teamRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateTeamIDInEmployee(
        employeeRepository: EmployeeRepository
    ): UpdateTeamIDInEmployee {
        return UpdateTeamIDInEmployee(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteEmployeeByID(
        employeeRepository: EmployeeRepository
    ): DeleteEmployeeByID {
        return DeleteEmployeeByID(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllEmployees(
        employeeRepository: EmployeeRepository
    ): GetAllEmployees {
        return GetAllEmployees(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetEmployeesByTeamID(
        employeeRepository: EmployeeRepository
    ): GetEmployeesByTeamID {
        return GetEmployeesByTeamID(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteTeam(
        teamRepository: TeamRepository
    ): DeleteTeam {
        return DeleteTeam(teamRepository)
    }

    /********************************************************************************************************************
     * Database
     ********************************************************************************************************************/


    @Provides
    @Singleton
    fun provideEmployeeManagementDatabase(
        application: Application
    ): EmployeeManagementDatabase {
        Log.i("AppModule", "Database initialized")
        return Room.databaseBuilder(
            context = application,
            klass = EmployeeManagementDatabase::class.java,
            name = "employee_management_database"
        )
            .addTypeConverter(PositionTypeConverter())
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun provideEmployeeDao(employeeManagementDatabase: EmployeeManagementDatabase): EmployeeDao {
        return employeeManagementDatabase.employeeDao()
    }

    @Provides
    @Singleton
    fun provideCredentialsDao(employeeManagementDatabase: EmployeeManagementDatabase): CredentialsDao {
        return employeeManagementDatabase.credentialsDao()
    }

    @Provides
    @Singleton
    fun provideTeamDao(employeeManagementDatabase: EmployeeManagementDatabase): TeamDao {
        return employeeManagementDatabase.teamDao()
    }


    /********************************************************************************************************************
     * Repository
     ********************************************************************************************************************/

    @Provides
    @Singleton
    fun provideEmployeeRepository(employeeDao: EmployeeDao): EmployeeRepository {
        return EmployeeRepositoryImpl(employeeDao = employeeDao)
    }

    @Provides
    @Singleton
    fun provideCredentialsRepository(credentialsDao: CredentialsDao): CredentialsRepository {
        return CredentialsRepositoryImpl(credentialsDao = credentialsDao)
    }

    @Provides
    @Singleton
    fun provideTeamRepository(teamDao: TeamDao): TeamRepository {
        return TeamRepositoryImpl(teamDao = teamDao)
    }
}