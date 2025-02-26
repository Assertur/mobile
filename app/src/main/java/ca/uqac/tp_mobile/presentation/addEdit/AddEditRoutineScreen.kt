package ca.uqac.tp_mobile.presentation.addEdit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_mobile.navigation.Screen

@Composable
fun AddEditStoryScreen(viewModel: AddEditRoutineViewModel, navController: NavController) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(
                Screen.ListRoutineScreen.route
            )
        }) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Save Story"
            )
        }
    }) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Text(
                text = "Add/Edit Story",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 36.sp, textAlign = TextAlign.Center
                )
            )
            OutlinedTextField(
                value = viewModel.routine.value.title,
                onValueChange = { viewModel.onEvent(AddEditRoutineEvent.EnteredTitle(it)) },
                label = { Text("Titre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.routine.value.description,
                onValueChange = { viewModel.onEvent(AddEditRoutineEvent.EnteredDescription(it)) },
                label = { Text("Description") },
                singleLine = false,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.routine.value.location,
                onValueChange = { viewModel.onEvent(AddEditRoutineEvent.EnteredDescription(it)) },
                label = { Text("Lieu") },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = viewModel.routine.value.priority.type.foregroundColor
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}