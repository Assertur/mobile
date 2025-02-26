package ca.uqac.tp_mobile.presentation.addEdit.fields.formats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun FormTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        // Affichage du placeholder
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = Color(0x80000547),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)  // Ajuste la position du placeholder
            )
        }

        // Champ de texte
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = Color(0xFF000547)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)  // Aligner correctement le texte
        )
    }
}