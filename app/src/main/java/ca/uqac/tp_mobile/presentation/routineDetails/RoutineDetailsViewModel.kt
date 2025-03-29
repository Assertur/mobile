package ca.uqac.tp_mobile.presentation.routineDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_mobile.domain.useCase.RoutineUseCases
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.deleteRoutineFromList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailsViewModel @Inject constructor(
    private val routineUseCases: RoutineUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _selectedRoutine = MutableStateFlow<RoutineVM?>(null)
    val selectedRoutine: StateFlow<RoutineVM?> = _selectedRoutine.asStateFlow()
    private val _routine = mutableStateOf(RoutineVM())
    val routine: State<RoutineVM> = _routine

    init {
        val routineId = savedStateHandle.get<Int>("routineId") ?: -1
        viewModelScope.launch(Dispatchers.IO) {
            val routineEntity = routineUseCases.getOneRoutine(routineId)
            _routine.value = routineEntity.let { RoutineVM.fromEntity(it) }
        }
    }

    private val _eventFlow = MutableSharedFlow<DetailsRoutineUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setSelectedRoutine(routine: RoutineVM) {
        _selectedRoutine.value = routine
    }

    fun onEvent(event: DetailsRoutineEvent) {
        if (event is DetailsRoutineEvent.Delete) {
            viewModelScope.launch {
                val entity = routine.value.toEntity()
                routineUseCases.deleteRoutine(entity)
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