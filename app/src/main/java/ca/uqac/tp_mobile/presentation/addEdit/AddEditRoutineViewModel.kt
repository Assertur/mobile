package ca.uqac.tp_mobile.presentation.addEdit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_mobile.presentation.Day
import ca.uqac.tp_mobile.presentation.Priority
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.addOrUpdateRoutine
import ca.uqac.tp_mobile.utils.getRoutineById

class AddEditRoutineViewModel(routineId: Int = -1) : ViewModel() {
    private val _routine = mutableStateOf(RoutineVM())
    val routine: State<RoutineVM> = _routine

    init {
        _routine.value = getRoutineById(routineId)
    }

    fun onEvent(event: AddEditRoutineEvent) {
        when (event) {
            is AddEditRoutineEvent.EnteredTitle -> {
                _routine.value = _routine.value.copy(title = event.title)
            }

            is AddEditRoutineEvent.EnteredDescription -> {
                _routine.value = _routine.value.copy(description = event.description)
            }

            is AddEditRoutineEvent.EnteredLocation -> {
                _routine.value = _routine.value.copy(location = event.location)
            }

            is AddEditRoutineEvent.EnteredHour -> {
                _routine.value = _routine.value.copy(hour = event.hour)
            }

            is AddEditRoutineEvent.EnteredDays -> {
                val dayToToggle = Day.fromString(event.day)
                _routine.value = _routine.value.copy(day = _routine.value.day.let { currentDays ->
                    if (currentDays.contains(dayToToggle)) {
                        currentDays - dayToToggle
                    } else {
                        currentDays + dayToToggle
                    }
                })

            }

            is AddEditRoutineEvent.EnteredPriority -> {
                _routine.value = _routine.value.copy(priority = Priority.fromString(event.priority))
            }

            //AddEditStoryEvent.StoryDone -> _story.value = _story.value.copy(done = !_story.value.done)
            AddEditRoutineEvent.SaveRoutine -> {
                addOrUpdateRoutine(routine.value)
            }
        }
    }
}