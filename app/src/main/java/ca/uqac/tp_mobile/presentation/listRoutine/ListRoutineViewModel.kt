package ca.uqac.tp_mobile.presentation.listRoutine

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.deleteRoutineFromList
import ca.uqac.tp_mobile.utils.getRoutines

class ListRoutineViewModel : ViewModel() {
    private val _routines: MutableState<List<RoutineVM>> =
        mutableStateOf(emptyList())
    var routines: State<List<RoutineVM>> = _routines

    init {
        _routines.value = loadRoutines()
    }

    private fun loadRoutines(): List<RoutineVM> {
        return getRoutines()
    }

    fun onEvent(event: RoutineEvent) {
        when (event) {
            is RoutineEvent.Delete -> {
                deleteStory(event.routine)
            }
        }
    }

    private fun deleteStory(routine: RoutineVM) {
        _routines.value =
            _routines.value.filter { it != routine } // FIXME : tout l'objet ou juste l'id ?
        deleteRoutineFromList(routine)
    }

    fun sortRoutines() {
        _routines.value = _routines.value.sortedBy { it.priority.value }
    }
}