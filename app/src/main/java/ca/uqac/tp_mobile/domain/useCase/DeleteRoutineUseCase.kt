package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.data.dao.RoutineDAO
import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.utils.RoutineException

class DeleteRoutineUseCase(private val routineDao: RoutineDAO) {
    @Throws(RoutineException::class)
    suspend operator fun invoke(routine: Routine) {
        routineDao.deleteRoutine(routine)
    }
}