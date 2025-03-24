package ca.uqac.tp_mobile.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.uqac.tp_mobile.model.Routine
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDAO {
    @Query("SELECT * FROM routine")
    fun getRoutines() : Flow<List<Routine>>

    @Query("SELECT * FROM routine WHERE ID = :id")
    fun getRoutine(id: Int) : Routine?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine): Long

    @Update
    suspend fun updateRoutine(routine: Routine)

    @Delete
    suspend fun deleteRoutine(routine: Routine)
}