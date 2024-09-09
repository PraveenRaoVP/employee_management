package android.caged.employeemanagement.data.local

import android.caged.employeemanagement.domain.model.Team
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Query("SELECT * FROM team WHERE teamName = :teamName LIMIT 1")
    suspend fun getTeamByName(teamName: String): Team?

    @Query("SELECT * FROM team WHERE teamId = :teamId LIMIT 1")
    suspend fun getTeamById(teamId: Long): Team?

    @Query("SELECT COUNT(*) FROM team")
    suspend fun getTeamCount(): Int

    @Query("SELECT * FROM team")
    suspend fun getAllTeams(): List<Team>

    @Query("DELETE FROM team WHERE teamId = :teamId")
    suspend fun deleteTeamById(teamId: Long)
}