package ca.uqac.tp_mobile.presentation.addEdit

import ca.uqac.tp_mobile.presentation.Reminder

sealed interface AddEditRoutineEvent {
    data class EnteredTitle(val title: String) : AddEditRoutineEvent
    data class EnteredDescription(val description: String) : AddEditRoutineEvent
    data class EnteredHour(val hour: String) : AddEditRoutineEvent
    data class EnteredDaily(val daily: Boolean) : AddEditRoutineEvent
    data class EnteredDays(val day: String) : AddEditRoutineEvent
    data class EnteredLocation(val location: String) : AddEditRoutineEvent
    data class EnteredPriority(val priority: String) : AddEditRoutineEvent
    data class AddReminder(val reminder : Reminder) : AddEditRoutineEvent
    data class RemoveReminder(val reminder : Reminder) : AddEditRoutineEvent
    data object SaveRoutine : AddEditRoutineEvent
}