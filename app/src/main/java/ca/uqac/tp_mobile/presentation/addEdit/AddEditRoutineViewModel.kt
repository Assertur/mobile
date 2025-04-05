package ca.uqac.tp_mobile.presentation.addEdit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_mobile.domain.useCase.RoutinesUseCases
import ca.uqac.tp_mobile.presentation.Day
import ca.uqac.tp_mobile.presentation.Priority
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.NotificationScheduler
import ca.uqac.tp_mobile.utils.RoutineException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRoutineViewModel @Inject constructor(
    private val routinesUseCases: RoutinesUseCases, savedStateHandle: SavedStateHandle,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {
    private val _routine = mutableStateOf(RoutineVM())
    val routine: State<RoutineVM> = _routine

    private val _eventFlow = MutableSharedFlow<AddEditRoutineUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val routineId = savedStateHandle.get<Int>("routineId") ?: -1
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val routineEntity = routinesUseCases.getOneRoutine(routineId)
                _routine.value = routineEntity.let { RoutineVM.fromEntity(it) }
            } catch (_: RoutineException) {
                _routine.value.id = -1
            }
        }
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
                _routine.value = _routine.value.copy(locationName = event.location)
            }

            is AddEditRoutineEvent.EnteredHour -> {
                _routine.value = _routine.value.copy(hour = event.hour)
            }

            is AddEditRoutineEvent.EnteredDaily -> {
                if (event.daily) {
                    _routine.value = _routine.value.copy(day = Day.getAllDays())
                } else {
                    _routine.value = _routine.value.copy(day = listOf())
                }
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

            AddEditRoutineEvent.SaveRoutine -> {
                viewModelScope.launch {
                    if (routine.value.title.isEmpty() || routine.value.description.isEmpty()) {
                        _eventFlow.emit(AddEditRoutineUiEvent.ShowMessage("Unable to save routine"))
                    } else {
                        val entity = routine.value.toEntity()
                        routinesUseCases.upsertRoutine(entity)
                        if (entity.id !== null) {
                            routine.value.id = entity.id!!
                        }
                        _eventFlow.emit(AddEditRoutineUiEvent.SavedRoutine)
                        notificationScheduler.scheduleRoutineNotification(routine.value)
                    }
                }
            }
        }
    }
}

sealed interface AddEditRoutineUiEvent {
    data class ShowMessage(val message: String) : AddEditRoutineUiEvent
    data object SavedRoutine : AddEditRoutineUiEvent
}