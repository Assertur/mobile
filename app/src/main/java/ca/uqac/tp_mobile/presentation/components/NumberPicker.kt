package ca.uqac.tp_mobile.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun NumberPicker(label: String, value: Int, range: IntRange, onChange: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label)
        OutlinedButton(onClick = { expanded = true }) {
            Text("$value")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            range.forEach {
                DropdownMenuItem(
                    text = { Text("$it") },
                    onClick = {
                        onChange(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
