package ca.uqac.tp_mobile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun BottomActionBar(onDelete: () -> Unit) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
        ,
        containerColor = Color(0xFF00141F)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = onDelete,
                    colors = IconButtonColors(
                        contentColor = Color(0xFFF95800),
                        containerColor = Color(0xFF00141F),
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
                Text("Supprimer",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFFF4F4FB)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}