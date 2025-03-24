package ca.uqac.tp_mobile.presentation.addEdit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_mobile.dao.RoutineDAO
import ca.uqac.tp_mobile.presentation.Day
import ca.uqac.tp_mobile.presentation.Priority
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.addOrUpdateRoutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddEditRoutineViewModel(val dao : RoutineDAO, routineId: Int = -1) : ViewModel() {
    private val _routine = mutableStateOf(RoutineVM())
    val routine: State<RoutineVM> = _routine

    private val _eventFlow = MutableSharedFlow<AddEditRoutineUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val routineEntity = dao.getRoutine(routineId)
            _routine.value = routineEntity?.let { RoutineVM.fromEntity(it) } ?: RoutineVM()

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

            AddEditRoutineEvent.SaveRoutine -> {
                viewModelScope.launch {
                    if (routine.value.title.isEmpty() || routine.value.description.isEmpty()) {
                        _eventFlow.emit(AddEditRoutineUiEvent.ShowMessage("Unable to save story"))
                    } else {
                        val entity = routine.value.toEntity()
                        if (entity.id === null){
                            routine.value.id = dao.insertRoutine(entity).toInt()
                        } else {
                            dao.updateRoutine(entity)
                        }
                        addOrUpdateRoutine(routine.value)
                        _eventFlow.emit(AddEditRoutineUiEvent.SavedRoutine)
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