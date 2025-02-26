package ca.uqac.tp_mobile.presentation.addEdit.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFF4F4FB))
            .clickable { onClick() }
            .border(2.dp, Color(0xFFD1D4FF), RoundedCornerShape(18.dp))
    ) {
        Spacer(modifier = Modifier.padding(horizontal = 15.dp, vertical = 40.dp))
        icon()
        Box(modifier = Modifier.weight(1f)) {
            field()
        }
        if (isRequired) {
            Text(
                "*",
                color = Color(0x80FF0000),
                modifier = Modifier.padding(start = 4.dp),
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.padding(horizontal = 15.dp))
    }
}
