package ca.uqac.tp_mobile.presentation.listRoutine

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_mobile.domain.useCase.RoutineUseCase
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.deleteRoutineFromList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListRoutineViewModel @Inject constructor
    (val routineUseCase: RoutineUseCase) : ViewModel() {
    private val _routines: MutableState<List<RoutineVM>> = mutableStateOf(emptyList())
    var routines: State<List<RoutineVM>> = _routines
    var job : Job? = null

    init {
        loadRoutines()
    }

    private fun loadRoutines() {
        job?.cancel()

        job = routineUseCase.getRoutines().onEach { routines ->
                _routines.value = routines.map {
                    RoutineVM.fromEntity(it)
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: RoutineEvent) {
        when (event) {
            is RoutineEvent.Delete -> {
                viewModelScope.launch {
                    val entity = event.routine.toEntity()
                    routineUseCase.deleteRoutine(entity)
                    deleteRoutine(event.routine)
                }
            }
        }
    }

    private fun deleteRoutine(routine: RoutineVM) {
        _routines.value =
            _routines.value.filter { it != routine }
        deleteRoutineFromList(routine)
    }

    fun sortRoutines() {
        _routines.value = _routines.value.sortedBy { it.priority.value }
    }
}