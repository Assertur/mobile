package ca.uqac.tp_mobile.presentation.addEdit
/*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_mobile.presentation.Day
import ca.uqac.tp_mobile.presentation.Priority
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.utils.addOrUpdateRoutine
import ca.uqac.tp_mobile.utils.getRoutineById

class FormAddEditRoutineViewModel : ViewModel() {


    var currentRoutineId: Int = -1

    //TODO : Vérifier les entrées pour plus de sécurité

    private val _title: MutableState<String> = mutableStateOf("")
    val title: State<String> = _title

    private val _desc: MutableState<String> = mutableStateOf("")
    val desc: State<String> = _desc

    private val _hour: MutableState<String> = mutableStateOf("")
    val hour: State<String> = _hour

    private val _date: MutableState<List<String>> = mutableStateOf(emptyList())
    val date: State<List<String>> = _date

    private val _location: MutableState<String> = mutableStateOf("")
    val location: State<String> = _location

    private val _priority: MutableState<String> = mutableStateOf("")
    val priority: State<String> = _priority

    private val _error: MutableState<String> = mutableStateOf("")
    val error: State<String> = _error


    fun onTitleChange(title: String) {
        _title.value = title
    }

    fun onDescChange(desc: String) {
        _desc.value = desc
    }

    fun onHourChange(hour: String) {
        _hour.value = hour
    }

    fun onDateChange(date: String) {
        if (date in _date.value) {
            _date.value = _date.value.filter { it != date }
        } else {
            _date.value += listOf(date)
        }
    }

    fun onLocationChange(location: String) {
        _location.value = location
    }

    fun onPriorityChange(priority: String) {
        _priority.value = priority
    }

    private fun resetFields() {
        _title.value = ""
        _desc.value = ""
        _hour.value = ""
        _date.value = emptyList()
        _location.value = ""
        _priority.value = ""
        _error.value = ""
    }

    fun loadRoutine(routineId: Int) {
        if (routineId != -1) {
            val routine = getRoutineById(routineId)
            routine?.let { r ->
                currentRoutineId = r.id
                _title.value = r.title
                _desc.value = r.description
                _hour.value = r.hour
                _date.value = r.day.map { it.label }
                _location.value = r.location
                _priority.value = r.priority.label
            }
        } else {
            resetFields()
            currentRoutineId = -1
        }
    }

    fun confirm(): Boolean {
        // ajouter hour, date et priority
        if (_priority.value.isBlank() || _title.value.isBlank() || _date.value.isEmpty() || _hour.value.isBlank() || _location.value.isBlank()) {
            _error.value = "Tous les champs avec * doivent être remplis."
            return false
        } else {
            val newRoutine = RoutineVM(
                currentRoutineId.takeIf { it != -1 } ?: (getLastId() + 1),
                _title.value,
                _desc.value,
                _date.value.map { dayString ->
                    Day.entries.find { it.label == dayString }
                        ?: throw IllegalArgumentException("Jour invalide: $dayString")
                },
                _hour.value,
                _location.value,
                priority = Priority.fromString(_priority.value)
            )
            val wasAdded = addOrUpdateRoutine(newRoutine)
            if (!wasAdded) {
                _error.value = "Une erreur est survenue. Veuillez réessayer."
                return false
            }
            resetFields()
            return true
        }
    }
}*/
