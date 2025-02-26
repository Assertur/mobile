package ca.uqac.tp_mobile.presentation.listRoutine

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_mobile.component.BottomActionBar
import ca.uqac.tp_mobile.component.RoutineCard
import ca.uqac.tp_mobile.navigation.Screen
import ca.uqac.tp_mobile.presentation.RoutineVM

@Composable
fun ListRoutineScreen(
    viewModel: ListRoutineViewModel,
    navController: NavController
) {
    var selectedRoutine by remember { mutableStateOf<List<RoutineVM>>(listOf()) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {navController.navigate(Screen.FormAddRoutine.route)}, containerColor = Color(0xFF00141F), shape = RoundedCornerShape(18.dp)) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add a story",
                    tint = Color(0xFFF4F4FB),
                    modifier = Modifier.padding(20.dp).size(35.dp))
            }
        },
        bottomBar = {
            if(selectedRoutine.isNotEmpty()) {
                BottomActionBar(
                    onDelete = {
                        selectedRoutine.forEach { routine ->
                            viewModel.onEvent(RoutineEvent.Delete(routine))
                        }
                        selectedRoutine = listOf()
                    }
                )
            }
        },
        containerColor = Color(0xFF000547)
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "User Stories",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                ),
                color = Color(244,244,251)
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(viewModel.routines.value, key = { it.id }) { routine ->
                    RoutineCard(
                        routine,
                        isSelected = routine.selected,
                        onLongPress = {
                            if (routine !in selectedRoutine) {
                                selectedRoutine = selectedRoutine + routine
                            } else {
                                val mutList = selectedRoutine.toMutableList()
                                mutList.remove(routine)
                                selectedRoutine = mutList.toList()
                            }
                        },
                       onClick = {
                           navController.navigate(Screen.RoutineDetails.createRoute(routine.id))
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}