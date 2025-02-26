package ca.uqac.tp_mobile.presentation

enum class Day(val index: Int, val label: String, val shortLabel: String) {
    LUNDI(1, "Lundi", "Lu."),
    MARDI(2, "Mardi", "Ma."),
    MERCREDI(3, "Mercredi", "Me."),
    JEUDI(4, "Jeudi", "Je."),
    VENDREDI(5, "Vendredi", "Ve."),
    SAMEDI(6, "Samedi", "Sa."),
    DIMANCHE(7, "Dimanche", "Di.")
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