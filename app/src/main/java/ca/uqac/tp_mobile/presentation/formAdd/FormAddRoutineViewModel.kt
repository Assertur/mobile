package ca.uqac.tp_mobile.presentation.formAdd

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.presentation.addOrUpdateRoutine
import ca.uqac.tp_mobile.presentation.getRoutines
import java.time.LocalDate
import java.time.LocalTime

class FormAddRoutineViewModel : ViewModel() {

    //Vérifier les entrées pour plus de sécurité

    private val _title : MutableState<String> = mutableStateOf("")
    val title : State<String> = _title

    private val _desc : MutableState<String> = mutableStateOf("")
    val desc : State<String> = _desc

    private val _hour : MutableState<String> = mutableStateOf("")
    val hour : State<String> = _hour

    private val _date : MutableState<List<String>> = mutableStateOf(emptyList())
    val date : State<List<String>> = _date

    private val _location : MutableState<String> = mutableStateOf("")
    val location : State<String> = _location

    private val _priority : MutableState<String> = mutableStateOf("")
    val priority : State<String> = _priority

    private val _error : MutableState<String> = mutableStateOf("")
    val error : State<String> = _error


    fun onTitleChange (title : String) {
        _title.value = title
    }

    fun onDescChange (desc : String) {
        _desc.value = desc
    }

    fun onHourChange (hour : String) {
        _hour.value = hour
    }

    fun onDateChange (date : String) {
        if (date in _date.value) {
            _date.value = _date.value.filter { it != date }
        }else{
            _date.value += listOf(date)
        }
    }

    fun onLocationChange (location : String) {
        _location.value = location
    }

    fun onPriorityChange (priority : String) {
        _priority.value = priority
    }

    fun confirm () : Boolean{
        // ajouter hour, date et priority
        if (_priority.value.isBlank() || _title.value.isBlank() || _date.value.isEmpty() || _hour.value.isBlank() || _location.value.isBlank())
        {
            _error.value = "Tous les champs avec * doivent être remplis"
            return false
        }else{
            val newRoutine = RoutineVM(getRoutines().size, _title.value, _desc.value)
            addOrUpdateRoutine(newRoutine)
            return true
        }
    }
}