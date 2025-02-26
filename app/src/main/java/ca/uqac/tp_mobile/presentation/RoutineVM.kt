package ca.uqac.tp_mobile.presentation

import androidx.compose.ui.graphics.Color

enum class Priority(val value: Int, val label: String, val type: PriorityType) {
    HAUTE(1, "Haute", HighPriority),
    MOYENNE(2, "Moyenne", StandardPriority),
    BASSE(3, "Basse", LowPriority);

    companion object {
        fun fromString(label: String): Priority {
            return entries.find { it.label.equals(label, ignoreCase = true) }
                ?: throw IllegalArgumentException("Priorité non valide: $label")
        }
    }
}


data class RoutineVM(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val day : List<Day> = listOf(),
    val hour: String = "",
    val location: String = "",
    val priority : Priority = Priority.BASSE,
    var selected : Boolean = false
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