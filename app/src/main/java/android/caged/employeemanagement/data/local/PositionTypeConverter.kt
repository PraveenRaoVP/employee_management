package android.caged.employeemanagement.data.local

import android.caged.employeemanagement.domain.model.Position
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class PositionTypeConverter {
    @TypeConverter
    fun positionToString(position: Position): String {
        return position.name
    }

    @TypeConverter
    fun stringToPosition(position: String): Position {
        return Position.valueOf(position)
    }
}