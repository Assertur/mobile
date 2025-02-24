package ca.uqac.tp_mobile.presentation.formAdd.fields.formats

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun FormTimeField(value: String, onTimeChange: (String) -> Unit, placeholder : String) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeChange(selectedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    Modifier.clickable{ timePickerDialog.show() }
    Box(modifier = Modifier.fillMaxWidth()) {
        // Affichage du placeholder
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)  // Ajuste la position du placeholder
            )
        }

        // Champ de texte
        BasicTextField(
            value = value,
            onValueChange = {},
            textStyle = TextStyle(color = Color.Black),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)  // Aligner correctement le texte
        )
    }
}