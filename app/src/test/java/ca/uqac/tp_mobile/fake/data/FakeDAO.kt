package ca.uqac.tp_mobile.fake.data

import ca.uqac.tp_mobile.data.dao.RoutineDAO
import ca.uqac.tp_mobile.domain.model.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDAO : RoutineDAO {

    private val routines = mutableListOf<Routine>()

    override fun getRoutines(): Flow<List<Routine>> = flow { emit(routines) }

    override suspend fun getRoutine(id: Int): Routine? {
        return routines.find { it.id == id }
    }

    override suspend fun insertRoutine(routine: Routine): Long {
        routines.removeAll { it.id == routine.id }
        routines.add(routine)
        return routine.id?.toLong() ?: -1
    }

    override suspend fun updateRoutine(routine: Routine) {
        routines.replaceAll { if (it.id == routine.id) routine else it }
    }

    override suspend fun deleteRoutine(routine: Routine) {
        routines.removeIf { it.id == routine.id }
    }

    fun clear() {
        routines.clear()
    }
}