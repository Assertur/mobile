package ca.uqac.tp_mobile.presentation

private val routineList: MutableList<RoutineVM> = mutableListOf(
    RoutineVM(
        id = 1,
        title = "Routine 1",
        description = "Morning workout",
        day = "Monday",
        hour = "07:00",
        location = "Gym",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 2,
        title = "Routine 2",
        description = "Work tasks",
        day = "Monday",
        hour = "09:00",
        location = "Work",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 3,
        title = "Routine 3",
        description = "Lunch break",
        day = "Monday",
        hour = "12:00",
        location = "Work",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 4,
        title = "Routine 4",
        description = "Meeting with team",
        day = "Monday",
        hour = "14:00",
        location = "Work",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 5,
        title = "Routine 5",
        description = "Afternoon workout",
        day = "Monday",
        hour = "17:00",
        location = "Home",
        priority = Priority.BASSE
    ),
    RoutineVM(
        id = 6,
        title = "Routine 6",
        description = "Dinner",
        day = "Monday",
        hour = "19:00",
        location = "Home",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 7,
        title = "Routine 7",
        description = "Reading time",
        day = "Tuesday",
        hour = "08:00",
        location = "Home",
        priority = Priority.BASSE
    ),
    RoutineVM(
        id = 8,
        title = "Routine 8",
        description = "Project work",
        day = "Tuesday",
        hour = "10:00",
        location = "Work",
        priority = Priority.HAUTE
    ),
    RoutineVM(
        id = 9,
        title = "Routine 9",
        description = "Family time",
        day = "Tuesday",
        hour = "18:00",
        location = "Park",
        priority = Priority.MOYENNE
    ),
    RoutineVM(
        id = 10,
        title = "Routine 10",
        description = "Evening relaxation",
        day = "Wednesday",
        hour = "21:00",
        location = "Park",
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