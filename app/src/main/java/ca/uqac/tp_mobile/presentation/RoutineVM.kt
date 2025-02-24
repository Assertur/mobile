package ca.uqac.tp_mobile.presentation

import androidx.compose.ui.graphics.Color

data class RoutineVM(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val day : String = "",
    val hour: String = "",
    val place: String = "",
    var selected : Boolean = false,
    val priorityType: PriorityType = StandardPriority
)

sealed class PriorityType(
    val backgroundColor: Color,
    val foregroundColor: Color,
    val selectedColor : Color
)

data object HighPriority:PriorityType(
    Color(255,180,223), Color(0,5,47),Color(0xFFD1D4FF))
data object StandardPriority:PriorityType(
    Color(255,250,180), Color(0,5,47),Color(0xFFD1D4FF))
data object LowPriority:PriorityType(
    Color(180,255,212),Color(0,5,47),Color(0xFFD1D4FF))