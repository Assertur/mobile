package ca.uqac.tp_mobile.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoutinePresentationField(icon: @Composable () -> Unit, titleText: String, contentText: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(horizontal = 15.dp, vertical = 40.dp))
            icon()
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(
                text = titleText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000547)
            )
        }
        Text(
            text = contentText,
            fontSize = 18.sp,
            color = Color(0xFF000547),
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp)
        )
        HorizontalDivider(thickness = 2.dp)
    }

}