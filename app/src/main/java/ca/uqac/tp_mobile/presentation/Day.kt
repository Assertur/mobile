package ca.uqac.tp_mobile.presentation

/**
 * Classe représentant les jours de la semaine.
 * @param index l'index du jour dans la semaine (pour tri des jours, exemple : 1 pour lundi)
 * @param label le nom du jour (exemple : lundi)
 * @param shortLabel le nom court du jour (exemple : Lu.)
 */
enum class Day(val index: Int, val label: String, val shortLabel: String) {
    LUNDI(1, "Lundi", "Lu."),
    MARDI(2, "Mardi", "Ma."),
    MERCREDI(3, "Mercredi", "Me."),
    JEUDI(4, "Jeudi", "Je."),
    VENDREDI(5, "Vendredi", "Ve."),
    SAMEDI(6, "Samedi", "Sa."),
    DIMANCHE(7, "Dimanche", "Di.");

    companion object {

        /**
         * Renvoie un jour de la semaine à partir d'une chaîne de caractères.
         * @param label le label du jour de la semaine (exemple : "Lundi")
         * @return le jour de la semaine de la classe Day correspondant
         * @exception IllegalArgumentException si le label précisé est incorrect
         */
        fun fromString(label: String): Day {
            return Day.entries.find { it.label.equals(label, ignoreCase = true) }
                ?: throw IllegalArgumentException("Jour non valide: $label")
        }

        /**
         * Renvoie un jour de la semaine à partir de son index.
         * @param id l'index du jour de la semaine (exemple : 1 pour lundi)
         * @return le jour de la semaine de la classe Day correspondant
         * @exception IllegalArgumentException si l'index n'est pas compris entre 1 et 7
         */
        private fun fromId(id: Int): Day {
            return Day.entries.find { it.index == id }
                ?: throw IllegalArgumentException("Jour non valide: $id")
        }

        /**
         * Renvoie une liste de jours de la semaine à partir de leurs indexs.
         * @param ids la liste des indexs des jours que l'on souhaite obtenir
         * @return la liste des jours de la semaine de la classe Day correspondants
         * @exception IllegalArgumentException si un index de la liste n'est pas compris entre 1 et 7
         */
        fun fromIds(ids: List<Int>): List<Day> {
            val days: MutableList<Day> = mutableListOf()
            ids.forEach {
                days.add(Day.fromId(it))
            }
            return days.toList()
        }
    }
}

/**
 * Prend une liste de jours et la formatte en chaîne de caractères pour afficher dans une routine :
 *   - le label complet s'il n'y a qu'un seul jour dans la liste
 *   - les labels cours s'il y a plusieurs jours dans la liste
 */
fun List<Day>.formatDaysForShortDisplay(): String {
    return when (size) {
        0 -> ""
        1 -> first().label
        else -> joinToString(" ") { it.shortLabel }
    }
}

/**
 * Prend une liste de jours et la formatte en chaîne de caractères pour afficher tous les labels
 */
fun List<Day>.formatDaysForLongDisplay(): String {
    return this.joinToString(", ") { it.label }
}