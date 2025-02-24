package ca.uqac.tp_mobile.presentation.listRoutine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_mobile.component.RoutineCard
import ca.uqac.tp_mobile.navigation.Screen

@Composable
fun ListRoutineScreen(
    viewModel: ListRoutineViewModel,
    navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {navController.navigate(Screen.FormAddRoutine.route)}) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add a story")
            }
        }
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
                )
            )
            LazyColumn {
                items(viewModel.routines.value) { routine ->
                    RoutineCard(routine, onClickDelete = {
                        viewModel.onEvent(RoutineEvent.Delete(routine))
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}