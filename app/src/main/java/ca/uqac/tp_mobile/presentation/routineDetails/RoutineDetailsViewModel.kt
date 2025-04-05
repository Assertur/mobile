package ca.uqac.tp_mobile.presentation.routineDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_mobile.domain.useCase.RoutinesUseCases
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.NotificationScheduler
import ca.uqac.tp_mobile.utils.RoutineException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailsViewModel @Inject constructor(
    private val routinesUseCases: RoutinesUseCases,
    savedStateHandle: SavedStateHandle,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {

    private val _selectedRoutine = MutableStateFlow<RoutineVM?>(null)
    val selectedRoutine: StateFlow<RoutineVM?> = _selectedRoutine.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DetailsRoutineUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val routineId = savedStateHandle.get<Int>("routineId") ?: -1
        fetchRoutineById(routineId)
    }

    /**
     * Charge la routine dqns le viewModel grâce à son ID.
     */
    fun fetchRoutineById(routineId: Int) {
        viewModelScope.launch {
            try {
                val routineEntity = routinesUseCases.getOneRoutine(routineId)
                _selectedRoutine.value = RoutineVM.fromEntity(routineEntity)
            } catch (e: RoutineException) {
                _eventFlow.emit(DetailsRoutineUiEvent.ShowMessage(e.message ?: "Unknown error"))
            } catch (e: Exception) {
                _eventFlow.emit(DetailsRoutineUiEvent.ShowMessage("An unexpected error occurred"))
            }
        }
    }

    fun onEvent(event: DetailsRoutineEvent) {
        when (event) {
            is DetailsRoutineEvent.Delete -> {
                viewModelScope.launch {
                    val entity = _selectedRoutine.value?.toEntity()
                        ?: throw RoutineException("No routine selected")
                    routinesUseCases.deleteRoutine(entity)
                    entity.id?.let { notificationScheduler.cancelRoutineNotification(it) }
                    _eventFlow.emit(DetailsRoutineUiEvent.Delete)
                }
            }
        }
    }
}

sealed interface DetailsRoutineUiEvent {
    data object Delete : DetailsRoutineUiEvent
    data class ShowMessage(val message: String) : DetailsRoutineUiEvent
}