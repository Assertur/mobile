package ca.uqac.tp_mobile.presentation.formAdd.fields.formats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormDropDownCheckField(
    selectedOptions: List<String>,
    onOptionChange: (String) -> Unit,
    options: List<String>,
    placeholder : String,
    expanded : MutableState<Boolean>
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(if (selectedOptions.isEmpty()) {
            placeholder
        }else{
            selectedOptions.joinToString(", ")
        })

        // Menu déroulant personnalisé
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
                                onOptionChange(option)  // Ajoute ou retire de la sélection
                            }
                            .padding(8.dp)
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Checkbox(
                            checked = selectedOptions.contains(option),  // Coche la checkbox si l'option est sélectionnée
                            onCheckedChange = {
                                onOptionChange(option)  // Met à jour la sélection
                            }
                        )
                    }
                }
            }
        }
    }
}
