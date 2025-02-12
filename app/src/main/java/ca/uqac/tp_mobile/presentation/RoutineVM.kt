package ca.uqac.tp_mobile.presentation

import java.util.Date

data class RoutineVM(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val done: Boolean = false,
    val hour: Date = Date()
)