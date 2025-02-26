package ca.uqac.tp_mobile.presentation.addEdit.fields.formats

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun FormTimeField(
    value: String,
    onTimeChange: (String) -> Unit,
    placeholder: String,
    expanded: MutableState<Boolean>
) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeChange(selectedTime)
            expanded.value = false
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(value.ifEmpty {
            placeholder
        }, color = Color(0xFF000547))
        if (expanded.value) {
            timePickerDialog.show()
        }
    }
}