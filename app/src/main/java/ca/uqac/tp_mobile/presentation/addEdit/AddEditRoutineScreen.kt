package ca.uqac.tp_mobile.presentation.addEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ca.uqac.tp_mobile.R
import ca.uqac.tp_mobile.navigation.Screen
import ca.uqac.tp_mobile.presentation.Day
import ca.uqac.tp_mobile.presentation.addEdit.fields.FormField
import ca.uqac.tp_mobile.presentation.addEdit.fields.formats.FormDropDownCheckField
import ca.uqac.tp_mobile.presentation.addEdit.fields.formats.FormDropDownRadioField
import ca.uqac.tp_mobile.presentation.addEdit.fields.formats.FormTextField
import ca.uqac.tp_mobile.presentation.addEdit.fields.formats.FormTimeField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditRoutineScreen(
    navController: NavController,
    viewModel: AddEditRoutineViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(
                viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)
            )
        }) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Save Routine"
            )
        }
    }) { contentPadding ->
        LaunchedEffect(true) {
            viewModel.eventFlow.collectLatest { event ->
                if (event is AddEditRoutineUiEvent.SavedRoutine) {
                        navController.navigate(Screen.ListRoutineScreen.route)
                    }
                else if (event is AddEditRoutineUiEvent.ShowMessage) {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Text(
                text = "Add/Edit Routine",
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
                value = viewModel.routine.value.locationName,
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
    val scrollState = rememberScrollState()

    val iconColor = Color(0xFF00141F)
    val buttonDefaultsColor = ButtonDefaults.buttonColors(containerColor = Color(0xFF00141F))
    val primaryTextColor = Color(0xFFF4F4FB)

    val hourExpanded: MutableState<Boolean> = remember { mutableStateOf(false) }
    val dateExpanded: MutableState<Boolean> = remember { mutableStateOf(false) }
    val priorityExpanded: MutableState<Boolean> = remember { mutableStateOf(false) }

    Scaffold(containerColor = Color(0xFF000547)) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Button(
                onClick = { navController.navigate(Screen.ListRoutineScreen.route) },
                colors = buttonDefaultsColor,
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour", tint = primaryTextColor
                )
            }

            Text(
                text = if (viewModel.routine.value.id == -1) {
                    "Ajouter une routine"
                } else {
                    "Modifier la routine"
                },
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 16.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    color = primaryTextColor,
                    fontWeight = FontWeight.Bold
                )
            )
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_title),
                            contentDescription = "Titre",
                            modifier = Modifier.size(24.dp),
                            tint = iconColor
                        )
                    },
                    isRequired = true,
                    field = {
                        FormTextField(
                            value = viewModel.routine.value.title,
                            onValueChange = { viewModel.onEvent(AddEditRoutineEvent.EnteredTitle(it)) },
                            placeholder = "Titre de la routine"
                        )
                    },
                    onClick = {/* pas d'action spécifique pour ce champ */ }
                )
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_subtitles),
                            contentDescription = "Description",
                            modifier = Modifier.size(24.dp),
                            tint = iconColor
                        )
                    },
                    isRequired = false,
                    field = {
                        FormTextField(
                            value = viewModel.routine.value.description,
                            onValueChange = {
                                viewModel.onEvent(
                                    AddEditRoutineEvent.EnteredDescription(
                                        it
                                    )
                                )
                            },
                            placeholder = "Description de la routine"
                        )
                    },
                    onClick = {/* pas d'action spécifique pour ce champ */ }
                )
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_access_time),
                            contentDescription = "Heure",
                            modifier = Modifier.size(24.dp),
                            tint = iconColor
                        )
                    },
                    isRequired = true,
                    field = {
                        FormTimeField(
                            value = viewModel.routine.value.hour,
                            onTimeChange = { viewModel.onEvent(AddEditRoutineEvent.EnteredHour(it)) },
                            placeholder = "Horaire de la routine",
                            expanded = hourExpanded
                        )
                    },
                    onClick = { hourExpanded.value = true }
                )
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_calendar_today),
                            contentDescription = "Date",
                            modifier = Modifier.size(24.dp),
                            tint = iconColor
                        )
                    },
                    isRequired = true,
                    field = {
                        FormDropDownCheckField(
                            selectedOptions = viewModel.routine.value.day.map { it.label },
                            onOptionChange = { viewModel.onEvent(AddEditRoutineEvent.EnteredDays(it)) },
                            options = Day.entries.map { it.label },
                            placeholder = "Jour(s) de la routine",
                            expanded = dateExpanded
                        )
                    },
                    onClick = { dateExpanded.value = true }
                )
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_location_on),
                            contentDescription = "Lieu",
                            modifier = Modifier.size(24.dp),
                            tint = iconColor
                        )
                    },
                    isRequired = true,
                    field = {
                        FormTextField(
                            value = viewModel.routine.value.locationName,
                            onValueChange = {
                                viewModel.onEvent(
                                    AddEditRoutineEvent.EnteredLocation(
                                        it
                                    )
                                )
                            },
                            placeholder = "Localisation de la routine"
                        )
                    },
                    onClick = { /**/ }
                )
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_priority_high),
                            contentDescription = "Priorité",
                            modifier = Modifier.size(24.dp),
                            tint = iconColor
                        )
                    },
                    isRequired = true,
                    field = {
                        FormDropDownRadioField(
                            value = viewModel.routine.value.priority.label,
                            onValueChange = { viewModel.onEvent(AddEditRoutineEvent.EnteredPriority(it)) },
                            options = listOf("Haute", "Moyenne", "Basse"),
                            placeholder = "Priorité",
                            expanded = priorityExpanded
                        )
                    }, onClick = { priorityExpanded.value = true }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)
                    navController.navigate(Screen.ListRoutineScreen.route)
                },
                colors = buttonDefaultsColor,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Sauvegarder",
                    color = primaryTextColor,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 18.dp)
                )
            }
        }
    }
}