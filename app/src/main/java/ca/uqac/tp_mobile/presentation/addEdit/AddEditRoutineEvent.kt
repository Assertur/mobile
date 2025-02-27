package ca.uqac.tp_mobile.presentation.addEdit

sealed interface AddEditRoutineEvent {
    data class EnteredTitle(val title: String) : AddEditRoutineEvent
    data class EnteredDescription(val description: String) : AddEditRoutineEvent
    data class EnteredHour(val hour: String) : AddEditRoutineEvent
    data class EnteredDays(val days: String) : AddEditRoutineEvent
    data class EnteredLocation(val location: String) : AddEditRoutineEvent
    data class EnteredPriority(val priority: String) : AddEditRoutineEvent
    data object SaveRoutine : AddEditRoutineEvent
}