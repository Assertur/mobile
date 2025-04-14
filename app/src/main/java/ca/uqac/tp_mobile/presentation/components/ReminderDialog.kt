package ca.uqac.tp_mobile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import ca.uqac.tp_mobile.presentation.Reminder

@Composable
fun ReminderDialog(
    onDismiss: () -> Unit,
    onConfirm: (Reminder) -> Unit
) {
    var days by remember { mutableStateOf(0) }
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouveau rappel") },
        text = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberPicker("J", days, 0..6) { days = it }
                NumberPicker("H", hours, 0..24) { hours = it }
                NumberPicker("M", minutes, 0..60) { minutes = it }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(Reminder(days, hours, minutes)) }) {
                Text("Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
