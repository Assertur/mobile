package ca.uqac.tp_mobile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_mobile.presentation.RoutineVM

@Composable
fun RoutineCard(routine : RoutineVM, onClickDelete: (RoutineVM) -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            color = Color.Gray,
            shape = RoundedCornerShape(10.dp)
        )
        .padding(16.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(routine.name,
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                if (routine.done){
                    Icon(Icons.Filled.Done, contentDescription = "Done")
                }
            }
            Text(routine.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.DarkGray)
        }
        IconButton(
            onClick = { onClickDelete(routine) },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Supprimer la story",
                tint = Color.Black
            )
        }
    }
}