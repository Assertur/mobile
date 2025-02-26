package ca.uqac.tp_mobile.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.currentCompositionLocalContext
import kotlin.coroutines.coroutineContext

private val routineList: MutableList<RoutineVM> = mutableListOf(
    RoutineVM(
        id = 1,
        title = "Routine 1",
        description = "Séance du matin",
        day = listOf(Day.LUNDI, Day.MARDI),
        hour = "07H00",
        location = "Gym",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 2,
        title = "Routine 2",
        description = "Tâches de travail",
        day = listOf(Day.LUNDI, Day.JEUDI, Day.DIMANCHE),
        hour = "09H00",
        location = "Travail",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 3,
        title = "Routine 3",
        description = "Pause déjeuner",
        day = listOf(Day.LUNDI),
        hour = "12H00",
        location = "Travail",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 4,
        title = "Routine 4",
        description = "Réunion avec l'équipe",
        day = listOf(Day.LUNDI),
        hour = "14H00",
        location = "Travail",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 5,
        title = "Routine 5",
        description = "Séance de l'après-midi",
        day = listOf(Day.LUNDI),
        hour = "17H00",
        location = "À la maison",
        priority = Priority.BASSE
    ),
    RoutineVM(
        id = 6,
        title = "Routine 6",
        description = "Dîner",
        day = listOf(Day.LUNDI),
        hour = "19H00",
        location = "À la maison",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 7,
        title = "Routine 7",
        description = "Temps de lecture",
        day = listOf(Day.MARDI),
        hour = "08H00",
        location = "À la maison",
        priority = Priority.BASSE
    ),
    RoutineVM(
        id = 8,
        title = "Routine 8",
        description = "Projet de travail",
        day = listOf(Day.MARDI),
        hour = "10H00",
        location = "Travail",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 9,
        title = "Routine 9",
        description = "Temps en famille",
        day = listOf(Day.MARDI),
        hour = "18H00",
        location = "Parc",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 10,
        title = "Routine 10",
        description = "Soirée relax",
        day = listOf(Day.MERCREDI),
        hour = "21H00",
        location = "Parc",
        priority = Priority.BASSE
    )
)

private var lastId: Int = 10

fun getRoutines(): List<RoutineVM> {
    return routineList
}

fun getRoutineById(id : Int) : RoutineVM? {
    return routineList.find{ it.id == id}
}

fun addOrUpdateRoutine(routine: RoutineVM) : Boolean {
    val existingStory = routineList.find { it.id == routine.id }
    existingStory?.let {
        routineList.remove(it)
    }
    val wasAdded = routineList.add(routine)
    Log.d(null, "La liste : $routineList")
    if (!wasAdded) {
        Log.d(null, "SKIBIDI DOP DOP")
        return false
    }
    else {
        Log.d(null, "LE WAS ADDED EST OK")
        lastId++
        return true
    }
}

fun deleteRoutineFromList(routine: RoutineVM) {
    routineList.remove(routine)
}

fun getLastId(): Int {
    return lastId
}