package ca.uqac.tp_mobile.presentation.formAdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_mobile.R
import ca.uqac.tp_mobile.navigation.Screen
import ca.uqac.tp_mobile.presentation.formAdd.fields.FormField
import ca.uqac.tp_mobile.presentation.formAdd.fields.formats.*

@Composable
fun FormAddRoutineScreen(
    viewModel: FormAddRoutineViewModel,
    navController: NavController
) {
    var hourExpanded : MutableState<Boolean> = remember { mutableStateOf(false) }
    var dateExpanded : MutableState<Boolean> = remember { mutableStateOf(false) }
    var priorityExpanded : MutableState<Boolean> = remember { mutableStateOf(false) }

    Scaffold(
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
            Button(onClick = {navController.navigate(Screen.ListRoutineScreen.route)}) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Add a story")
            }

            if (viewModel.error.value.isNotEmpty()){
                Text(viewModel.error.value)
            }

            Text(
                text = "Ajouter une routine",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )
            )
            Column {
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_title),
                            contentDescription = "Titre",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    isRequired = true,
                    field = {
                        FormTextField(
                            value = viewModel.title.value,
                            onValueChange = { newValue -> viewModel.onTitleChange(newValue) },
                            placeholder = "Titre de la routine"
                        )
                    },
                    onClick = { /* pas d'action spécifique pour ce champ */ }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.outline_subtitles),
                        contentDescription = "Description",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = false,
                    field = { FormTextField(
                        value = viewModel.desc.value,
                        onValueChange = { newValue -> viewModel.onDescChange(newValue) },
                        placeholder = "Description de la routine") },
                    onClick = { /* pas d'action spécifique pour ce champ */ }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.outline_access_time),
                        contentDescription = "Heure",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormTimeField(
                        value = viewModel.hour.value,
                        onTimeChange = { newValue -> viewModel.onHourChange(newValue) },
                        placeholder = "Horaire de la routine",
                        expanded = hourExpanded
                    ) },
                    onClick = { hourExpanded.value = true }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.outline_calendar_today),
                        contentDescription = "Date",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormDropDownCheckField(
                        selectedOptions = viewModel.date.value,
                        onOptionChange = { newValue -> viewModel.onDateChange(newValue) },
                        options = listOf("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"),
                        placeholder = "Jour(s) de la routine",
                        expanded = dateExpanded
                    ) },
                    onClick = { dateExpanded.value = true }
                )
                FormField(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_location_on),
                            contentDescription = "Lieu",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    isRequired = true,
                    field = {
                        FormTextField(
                            value = viewModel.location.value,
                            onValueChange = { newValue -> viewModel.onLocationChange(newValue) },
                            placeholder = "Description de la routine"
                        )
                    },
                    onClick = { /**/ }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.outline_priority_high),
                        contentDescription = "Priorité",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormDropDownRadioField(
                        value = viewModel.priority.value.toString(),
                        onValueChange = { newValue -> viewModel.onPriorityChange(newValue) },
                        options = listOf("Haute","Moyenne","Basse"),
                        placeholder = "Priorité",
                        expanded = priorityExpanded
                    ) }, onClick = { priorityExpanded.value  = true }
                )
                Button(onClick = {
                    if (viewModel.confirm()) {
                        navController.navigate(Screen.ListRoutineScreen.route)
                    }
                }) {
                    Text("Sauvegarder")
                }
            }
        }
    }
}