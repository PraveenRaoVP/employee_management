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
import android.caged.employeemanagement.domain.usecases.application.FindEmployeeById
import android.caged.employeemanagement.domain.usecases.application.GetCredentials
import android.caged.employeemanagement.domain.usecases.application.GetEmployeeById
import android.caged.employeemanagement.domain.usecases.application.GetTeamDetailsById
import android.caged.employeemanagement.domain.usecases.application.UpdateProfileImage
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

    @Provides
    @Singleton
    fun provideApplicationUseCases(
        findEmployeeById: FindEmployeeById,
        getCredentials: GetCredentials,
        getEmployeeById: GetEmployeeById,
        getTeamDetailsById: GetTeamDetailsById,
        updateProfileImage: UpdateProfileImage
    ): ApplicationUseCases {
        return ApplicationUseCases(
            findEmployeeById = findEmployeeById,
            getCredentials = getCredentials,
            getEmployeeById = getEmployeeById,
            getTeamDetailsById = getTeamDetailsById,
            updateProfileImage = updateProfileImage
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
    fun provideUpdateProfileImage(
        employeeRepository: EmployeeRepository
    ): UpdateProfileImage {
        return UpdateProfileImage(employeeRepository)
    }

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