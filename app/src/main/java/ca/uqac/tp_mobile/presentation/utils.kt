package ca.uqac.tp_mobile.presentation

private val routineList: MutableList<RoutineVM> = mutableListOf(
    RoutineVM(
        id = 1,
        name = "Routine 1",
        description = "Morning workout",
        day = "Monday",
        hour = "07:00",
        place = "Gym",
        priorityType = HighPriority),
    RoutineVM(
        id = 2,
        name = "Routine 2",
        description = "Work tasks",
        day = "Monday",
        hour = "09:00",
        place = "Work",
        priorityType = StandardPriority),
    RoutineVM(
        id = 3,
        name = "Routine 3",
        description = "Lunch break",
        day = "Monday",
        hour = "12:00",
        place = "Work",
        priorityType = StandardPriority),
    RoutineVM(
        id = 4,
        name = "Routine 4",
        description = "Meeting with team",
        day = "Monday",
        hour = "14:00",
        place = "Work",
        priorityType = HighPriority),
    RoutineVM(
        id = 5,
        name = "Routine 5",
        description = "Afternoon workout",
        day = "Monday",
        hour = "17:00",
        place = "Home",
        priorityType = LowPriority),
    RoutineVM(
        id = 6,
        name = "Routine 6",
        description = "Dinner",
        day = "Monday",
        hour = "19:00",
        place = "Home",
        priorityType = StandardPriority),
    RoutineVM(
        id = 7,
        name = "Routine 7",
        description = "Reading time",
        day = "Tuesday",
        hour = "08:00",
        place = "Home",
        priorityType = LowPriority),
    RoutineVM(
        id = 8,
        name = "Routine 8",
        description = "Project work",
        day = "Tuesday",
        hour = "10:00",
        place = "Work",
        priorityType = HighPriority),
    RoutineVM(
        id = 9,
        name = "Routine 9",
        description = "Family time",
        day = "Tuesday",
        hour = "18:00",
        place = "Park",
        priorityType = StandardPriority),
    RoutineVM(
        id = 10,
        name = "Routine 10",
        description = "Evening relaxation",
        day = "Wednesday",
        hour = "21:00",
        place = "Park",
        priorityType = LowPriority)
)
fun getRoutines() : List<RoutineVM> {
    return routineList
}
fun addOrUpdateRoutine(routine: RoutineVM) {
    val existingStory = routineList.find { it.id == routine.id }
    existingStory?.let {
        routineList.remove(it)
    }
    routineList.add(routine)
}

fun deleteRoutineFromList(routine: RoutineVM) {
    routineList.remove(routine)
}