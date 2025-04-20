package ca.uqac.tp_mobile.domain.useCase

data class RoutinesUseCases(
    val getRoutines: GetRoutinesUseCase,
    val getOneRoutine: GetOneRoutineUseCase,
    val upsertRoutine: UpsertRoutineUseCase,
    val deleteRoutine: DeleteRoutineUseCase
)