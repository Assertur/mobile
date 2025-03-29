package ca.uqac.tp_mobile.domain.useCase

data class RoutineUseCases (
    val getRoutines : GetRoutinesUseCase,
    val getOneRoutine : GetOneRoutineUseCase,
    val upsertRoutine : UpsertRoutineUseCase,
    val deleteRoutine : DeleteRoutineUseCase
)