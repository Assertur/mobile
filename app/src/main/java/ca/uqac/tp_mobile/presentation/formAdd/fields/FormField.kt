package ca.uqac.tp_mobile.presentation.formAdd.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FormField(
    icon: @Composable () -> Unit,
    isRequired: Boolean,
    onClick: () -> Unit,
    field: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
    ) {
        icon()
        Box(modifier = Modifier.weight(1f)) {
            field()
        }
        if (isRequired) {
            Text("*", color = Color.Red, modifier = Modifier.padding(start = 4.dp))
        }
    }
}
