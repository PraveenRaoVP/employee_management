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
import android.caged.employeemanagement.domain.usecases.application.CredentialUseCases
import android.caged.employeemanagement.domain.usecases.application.EmployeeUseCases
import android.caged.employeemanagement.domain.usecases.application.TeamUseCases
import android.content.Context
import android.util.Log
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
        employeeUseCases: EmployeeUseCases,
        teamUseCases: TeamUseCases,
        credentialUseCases: CredentialUseCases
    ): ApplicationUseCases {
        return ApplicationUseCases(
            employeeUseCases = employeeUseCases,
            teamUseCases = teamUseCases,
            credentialUseCases = credentialUseCases
        )
    }

    @Provides
    @Singleton
    fun provideCredentialUseCases(
        credentialsRepository: CredentialsRepository
    ) : CredentialUseCases {
        return CredentialUseCases(credentialsRepository)
    }

    @Provides
    @Singleton
    fun provideEmployeeUseCases(
        employeeRepository: EmployeeRepository
    ) : EmployeeUseCases {
        return EmployeeUseCases(
            employeeRepository = employeeRepository
        )
    }

    @Provides
    @Singleton
    fun provideTeamUseCases(
        teamRepository: TeamRepository
    ) : TeamUseCases{
        return TeamUseCases(
            teamRepository
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