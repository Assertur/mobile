package ca.uqac.tp_mobile.presentation

private val routineList: MutableList<RoutineVM> = mutableListOf(
    RoutineVM(id = 1,
        "Inscription",
        description = "En tant que utilisateur, \nje veux créer un compte.",
        done = false),
    RoutineVM(id = 2,
        "Consulter le solde",
        description = "En tant que client, \nje veux voir mon solde.",
        done = true,
    ),
    RoutineVM(id = 3,
        "Notifications",
        description =
        "En tant que abonné, \nje veux recevoir des notifications",
        done = true,
    ),
    RoutineVM(id = 4,
        "Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles",
        done = true,
    )
)
fun getRoutines() : List<RoutineVM> {
    return routineList;
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