package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.data.dao.RoutineDAO
import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.utils.RoutineException

class GetOneRoutineUseCase (private val routineDao : RoutineDAO) {
    suspend operator fun invoke(id: Int) : Routine {
        val entity = routineDao.getRoutine(id)
        if (entity === null) {
            throw RoutineException("Unknown routine")
        } else {
            return entity
        }
    }
}