package ca.uqac.tp_mobile.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ca.uqac.tp_mobile.presentation.RoutineVM

@Composable
fun DefaultTextField(routine: RoutineVM) {
    OutlinedTextField(
        value = routine.title,
        onValueChange = {},
        label = { Text("Title") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
