package ca.uqac.tp_mobile.presentation

import androidx.compose.ui.graphics.Color
import ca.uqac.tp_mobile.domain.model.Routine
import kotlin.random.Random

enum class Priority(val value: Int, val label: String, val type: PriorityType) {
    HAUTE(1, "Haute", HighPriority), MOYENNE(2, "Moyenne", StandardPriority), BASSE(
        3, "Basse", LowPriority
    );

    companion object {
        fun fromString(label: String): Priority {
            return entries.find { it.label.equals(label, ignoreCase = true) }
                ?: throw IllegalArgumentException("Priorité non valide: $label")
        }
        fun fromInt(priority : Int) : Priority {
            return when (priority) {
                1 -> HAUTE
                2 -> MOYENNE
                3 -> BASSE
                else -> throw IllegalArgumentException("Priorité non valide: $priority")
            }
        }
    }
}

data class RoutineVM(
    var id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val day: List<Day> = listOf(),
    val hour: String = "",
    val location: String = "",
    val priority: Priority = Priority.MOYENNE,
    var selected: Boolean = false
) {

    fun toEntity(): Routine {
        return Routine(
            id = if (id < 0) null else id,
            title = title,
            description = description,
            day = day.map { it.index },
            hour = hour,
            location = location,
            priority = priority.value
        )
    }

    companion object{
        fun fromEntity(entity : Routine) : RoutineVM {
            return RoutineVM(
                id = entity.id!!,
                title = entity.title,
                description = entity.description,
                day = Day.fromIds(entity.day),
                hour = entity.hour,
                location = entity.location,
                priority = Priority.fromInt(entity.priority)
            )
        }
    }
}

sealed class PriorityType(
    val backgroundColor: Color, val foregroundColor: Color, val selectedColor: Color
)

data object HighPriority : PriorityType(
    Color(255, 180, 223), Color(0, 5, 47), Color(0xFFD1D4FF)
)

data object StandardPriority : PriorityType(
    Color(255, 250, 180), Color(0, 5, 47), Color(0xFFD1D4FF)
)

data object LowPriority : PriorityType(
    Color(180, 255, 212), Color(0, 5, 47), Color(0xFFD1D4FF)
)