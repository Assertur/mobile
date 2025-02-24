package ca.uqac.tp_mobile.presentation.formAdd.fields

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FormField(icon: @Composable () -> Unit, isRequired: Boolean, field : @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        icon()
        field()
        if (isRequired) {
            Text("*", color = Color.Red, modifier = Modifier.padding(start = 4.dp))
        }
    }
}