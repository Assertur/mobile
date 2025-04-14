package ca.uqac.tp_mobile.presentation

import androidx.compose.ui.graphics.Color
import ca.uqac.tp_mobile.domain.model.Routine
import kotlin.random.Random

/**
 * Classe enum qui liste les priorités des routines (basse, moyenne, haute).
 * @param value la valeur de la priorité (exemple : 1 pour Haute)
 * @param label le label de la priorite (exemple : Haute)
 * @param type le type de priorité correspondant (exemple : PriorityType.HighPriority)
 */
enum class Priority(val value: Int, val label: String, val type: PriorityType) {
    HAUTE(1, "Haute", HighPriority), MOYENNE(2, "Moyenne", StandardPriority), BASSE(
        3, "Basse", LowPriority
    );

    companion object {

        /**
         * Renvoie une priorité correspondante au label demandé.
         * @param label le label de la priorité
         * @return la priorité de la classe Priority correspondante
         * @exception IllegalArgumentException si le label n'est pas dans la classe Priority
         */
        fun fromString(label: String): Priority {
            return entries.find { it.label.equals(label, ignoreCase = true) }
                ?: throw IllegalArgumentException("Priorité non valide: $label")
        }

        /**
         * Renvoie une priorité correspondante à l'id (index) demandé.
         * @param priority l'index de la priorité (entre 1 et 3, du plus haut au plus bas)
         * @return la priorité correspondante
         * @exception IllegalArgumentException si l'index n'est pas compris entre 1 et 3
         */
        fun fromInt(priority: Int): Priority {
            return when (priority) {
                1 -> HAUTE
                2 -> MOYENNE
                3 -> BASSE
                else -> throw IllegalArgumentException("Priorité non valide: $priority")
            }
        }
    }
}

data class Reminder(val days: Int, val hours: Int, val minutes: Int)

/**
 * Classe représentant une routine pour les viewModels.
 * @param id son id (définit par la BD)
 * @param title son nom (titre)
 * @param description sa description
 * @param day son(ses) jour(s) concerné(s) par la routine
 * @param hour son heure
 * @param locationName le nom de son lieu
 * @param locationLat la latitude de son lieu
 * @param locationLng la longitude de son lieu
 * @param priority sa priorité
 * @param selected vrai si elle est actuellement sélectionnée, faux sinon
 * @param reminders ses rappels
 */
data class RoutineVM(
    var id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val day: List<Day> = listOf(),
    val hour: String = "",
    val locationName: String = "",
    val locationLat: Double = 0.0,
    val locationLng: Double = 0.0,
    val priority: Priority = Priority.MOYENNE,
    var selected: Boolean = false,
    val reminders: List<Reminder> = emptyList()
) {

    /**
     * Transforme une RoutineVM en entité Routine pour la BD.
     */
    fun toEntity(): Routine {
        return Routine(
            id = if (id < 0) null else id,
            title = title,
            description = description,
            day = day.map { it.index },
            hour = hour,
            locationName = locationName,
            locationLat = locationLat,
            locationLng = locationLng,
            priority = priority.value
        )
    }

    companion object {
        fun fromEntity(entity: Routine): RoutineVM {
            return RoutineVM(
                id = entity.id!!,
                title = entity.title,
                description = entity.description,
                day = Day.fromIds(entity.day),
                hour = entity.hour,
                locationName = entity.locationName,
                locationLat = entity.locationLat,
                locationLng = entity.locationLng,
                priority = Priority.fromInt(entity.priority)
            )
        }
    }
}

/**
 * Définit les couleurs à appliquer à la routine.
 */
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