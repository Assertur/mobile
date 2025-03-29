package ca.uqac.tp_mobile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_mobile.R
import ca.uqac.tp_mobile.presentation.Priority

@Composable
fun RoutinePresentationPriorityField(priority: Priority) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier.padding(
                    horizontal = 15.dp, vertical = 40.dp
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.outline_priority_high),
                contentDescription = "Priorité",
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF000547)
            )
            Text(
                text = "Priorité",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000547)
            )
        }
        Box(Modifier.background(priority.type.backgroundColor)) {
            Text(
                text = priority.label,
                fontSize = 18.sp,
                color = Color(0xFF000547),
            )
        }
    }
}