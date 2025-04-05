package ca.uqac.tp_mobile.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.uqac.tp_mobile.data.dao.RoutineDAO
import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.utils.Converters

@Database(entities = [Routine::class], version = 2)
@TypeConverters(Converters::class)
abstract class RoutineDatabase : RoomDatabase() {

    abstract val dao: RoutineDAO

    companion object {
        const val DATABASE_NAME = "routine.db"
    }

}