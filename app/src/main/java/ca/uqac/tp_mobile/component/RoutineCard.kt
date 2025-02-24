package ca.uqac.tp_mobile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_mobile.R
import ca.uqac.tp_mobile.presentation.RoutineVM

    @Composable
    fun RoutineCard(routine : RoutineVM, isSelected : Boolean, onLongPress : () -> Unit, onClick : () -> Unit) {
        val backgroundColor = if (isSelected) {
            remember { mutableStateOf(routine.priorityType.selectedColor) } // Couleur sélectionnée
        } else {
            remember { mutableStateOf(routine.priorityType.backgroundColor) } // Couleur de fond par défaut
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                color = backgroundColor.value,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = {
                        onLongPress()
                        if (!routine.selected) {
                            backgroundColor.value = routine.priorityType.selectedColor
                        } else {
                            backgroundColor.value = routine.priorityType.backgroundColor
                        }
                        routine.selected = !routine.selected
                    }
                )
            }
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(routine.name,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = routine.priorityType.foregroundColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "Place")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(routine.place,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = routine.priorityType.foregroundColor)
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(painter = painterResource(id = R.drawable.outline_access_time), contentDescription = "Date")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        routine.day,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = routine.priorityType.foregroundColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        routine.hour,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = routine.priorityType.foregroundColor
                    )
                }
            }
        }
    }