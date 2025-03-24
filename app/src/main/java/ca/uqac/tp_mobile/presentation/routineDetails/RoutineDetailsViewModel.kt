package ca.uqac.tp_mobile.presentation.routineDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_mobile.dao.RoutineDAO
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.deleteRoutineFromList
import ca.uqac.tp_mobile.utils.getRoutineById
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoutineDetailsViewModel(val dao : RoutineDAO, routineId :Int) : ViewModel() {
    private val _selectedRoutine = MutableStateFlow<RoutineVM?>(null)
    val selectedRoutine: StateFlow<RoutineVM?> = _selectedRoutine.asStateFlow()

    val routine = getRoutineById(routineId)
    private val _eventFlow = MutableSharedFlow<DetailsRoutineUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setSelectedRoutine(routine: RoutineVM) {
        _selectedRoutine.value = routine
    }

    fun onEvent(event: DetailsRoutineEvent) {
        if (event is DetailsRoutineEvent.Delete){
            viewModelScope.launch {
                val entity = routine.toEntity()
                dao.deleteRoutine(entity)
                deleteRoutineFromList(RoutineVM.fromEntity(entity))
                _eventFlow.emit(DetailsRoutineUiEvent.Delete)
            }
        }
    }
}

sealed interface DetailsRoutineUiEvent {
    data object Delete : DetailsRoutineUiEvent
    data class ShowMessage(val message: String) : DetailsRoutineUiEvent
}