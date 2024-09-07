package android.caged.employeemanagement.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class Credentials(
    @PrimaryKey(autoGenerate = true)
    val credentialID: Long = 0,
    val employeeID: Long,
    val password: String
)
