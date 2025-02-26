package ca.uqac.tp_mobile.presentation

private val routineList: MutableList<RoutineVM> = mutableListOf(
    RoutineVM(
        id = 1,
        title = "Routine 1",
        description = "Séance du matin",
        day = "Lundi",
        hour = "07H00",
        location = "Gym",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 2,
        title = "Routine 2",
        description = "Tâches de travail",
        day = "Lundi",
        hour = "09H00",
        location = "Travail",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 3,
        title = "Routine 3",
        description = "Pause déjeuner",
        day = "Lundi",
        hour = "12H00",
        location = "Travail",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 4,
        title = "Routine 4",
        description = "Réunion avec l'équipe",
        day = "Lundi",
        hour = "14H00",
        location = "Travail",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 5,
        title = "Routine 5",
        description = "Séance de l'après-midi",
        day = "Lundi",
        hour = "17H00",
        location = "À la maison",
        priority = Priority.BASSE
    ),
    RoutineVM(
        id = 6,
        title = "Routine 6",
        description = "Dîner",
        day = "Lundi",
        hour = "19H00",
        location = "À la maison",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 7,
        title = "Routine 7",
        description = "Temps de lecture",
        day = "Mardi",
        hour = "08H00",
        location = "À la maison",
        priority = Priority.BASSE
    ),
    RoutineVM(
        id = 8,
        title = "Routine 8",
        description = "Projet de travail",
        day = "Mardi",
        hour = "10H00",
        location = "Travail",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 9,
        title = "Routine 9",
        description = "Temps en famille",
        day = "Mardi",
        hour = "18H00",
        location = "Parc",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 10,
        title = "Routine 10",
        description = "Soirée relax",
        day = "Mercredi",
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

fun addOrUpdateRoutine(routine: RoutineVM) {
    val existingStory = routineList.find { it.id == routine.id }
    existingStory?.let {
        routineList.remove(it)
    }
    routineList.add(routine)
    lastId++
}

fun deleteRoutineFromList(routine: RoutineVM) {
    routineList.remove(routine)
}

fun getLastId(): Int {
    return lastId
}