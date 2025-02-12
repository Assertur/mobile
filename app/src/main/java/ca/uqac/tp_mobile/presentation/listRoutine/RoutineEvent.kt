package ca.uqac.tp_mobile.presentation.listRoutine

import ca.uqac.tp_mobile.presentation.RoutineVM

sealed class RoutineEvent {
    data class Delete(val routine: RoutineVM) : RoutineEvent()
}