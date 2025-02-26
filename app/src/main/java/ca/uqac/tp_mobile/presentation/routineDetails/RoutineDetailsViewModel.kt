package ca.uqac.tp_mobile.presentation.routineDetails

import androidx.lifecycle.ViewModel
import ca.uqac.tp_mobile.presentation.RoutineVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoutineDetailsViewModel : ViewModel() {
    private val _selectedRoutine = MutableStateFlow<RoutineVM?>(null)
    val selectedRoutine: StateFlow<RoutineVM?> = _selectedRoutine.asStateFlow()

    fun setSelectedRoutine(routine: RoutineVM) {
        _selectedRoutine.value = routine
    }
}