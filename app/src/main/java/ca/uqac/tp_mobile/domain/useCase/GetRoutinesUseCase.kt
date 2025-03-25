package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.data.dao.RoutineDAO
import ca.uqac.tp_mobile.domain.model.Routine
import kotlinx.coroutines.flow.Flow

class GetRoutinesUseCase (private val routineDao : RoutineDAO) {
    operator fun invoke() : Flow<List<Routine>> {
        return routineDao.getRoutines()
    }
}