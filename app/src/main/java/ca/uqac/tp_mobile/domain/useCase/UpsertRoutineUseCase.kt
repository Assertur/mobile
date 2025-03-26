package ca.uqac.tp_mobile.domain.useCase

import ca.uqac.tp_mobile.data.dao.RoutineDAO
import ca.uqac.tp_mobile.domain.model.Routine
import ca.uqac.tp_mobile.utils.RoutineException

class UpsertRoutineUseCase (private val routineDao : RoutineDAO) {
    @Throws(RoutineException::class)
    suspend operator fun invoke(routine: Routine) {
        if (routine.title.isEmpty() || routine.description.isEmpty())
            throw RoutineException("Routine data is invalid")
        if (routine.id === null || routine.id!! < 0)
            routine.id = routineDao.insertRoutine(routine).toInt()
        else
            routineDao.updateRoutine(routine)
    }
}