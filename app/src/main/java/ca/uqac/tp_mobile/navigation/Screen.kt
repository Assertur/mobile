package ca.uqac.tp_mobile.navigation

sealed class Screen(val route: String) {
    data object ListRoutineScreen : Screen("list_routine_screen")
    data object FormAddRoutine : Screen("form_add_screen")
    data object RoutineDetails : Screen("routine_details_screen/{routineId}") {
        fun createRoute(routineId: Int) = "routine_details_screen/$routineId"
    }
}