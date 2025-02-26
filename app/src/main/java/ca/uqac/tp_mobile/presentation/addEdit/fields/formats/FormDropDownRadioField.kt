package ca.uqac.tp_mobile.presentation.addEdit.fields.formats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FormDropDownRadioField(
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    placeholder: String,
    expanded: MutableState<Boolean>
) {

    val textColor = Color(0xFF000547)

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(value.ifEmpty {
            placeholder
        }, color = textColor)

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            Column {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onValueChange(option)  // Met à jour la valeur sélectionnée
                                expanded.value = false        // Ferme le menu après sélection
                            }
                            .padding(8.dp)
                    ) {
                        Text(option, color = textColor)
                    }
                }
            }
        }
    }
}