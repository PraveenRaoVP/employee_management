package android.caged.employeemanagement.data.local

import android.caged.employeemanagement.domain.model.Credentials
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CredentialsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredentials(credentials: Credentials)

    @Query("SELECT * FROM credentials WHERE employeeID = :employeeId LIMIT 1")
    suspend fun getCredentialsByEmployeeId(employeeId: Long): Credentials?

    @Query("UPDATE credentials SET password = :password WHERE employeeID = :employeeId")
    suspend fun updatePassword(employeeId: Long, password: String)
}