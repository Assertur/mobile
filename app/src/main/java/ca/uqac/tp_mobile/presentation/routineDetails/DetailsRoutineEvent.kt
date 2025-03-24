package ca.uqac.tp_mobile.presentation.routineDetails

sealed interface DetailsRoutineEvent {
    data object Delete : DetailsRoutineEvent
}