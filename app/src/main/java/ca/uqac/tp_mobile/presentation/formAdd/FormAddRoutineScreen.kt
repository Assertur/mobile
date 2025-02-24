package ca.uqac.tp_mobile.presentation.formAdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Scaffold(
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
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
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Titre",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormTextField(
                        value = viewModel.title.value,
                        onValueChange = { newValue -> viewModel.onTitleChange(newValue) },
                        placeholder = "Titre de la routine") }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Description",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = false,
                    field = { FormTextField(
                        value = viewModel.desc.value,
                        onValueChange = { newValue -> viewModel.onDescChange(newValue) },
                        placeholder = "Description de la routine") }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Heure",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormTimeField(
                        value = viewModel.hour.value,
                        onTimeChange = { newValue -> viewModel.onHourChange(newValue) },
                        placeholder = "Horaire de la routine") }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Date",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormDropDownCheckField(
                        selectedOptions = viewModel.date.value,
                        onOptionChange = { newValue -> viewModel.onDateChange(newValue) },
                        options = listOf("Lundi","Mardi","Mercrdi","Jeudi","Vendredi","Samedi","Dimanche"),
                        placeholder = "Jour(s) de la routine"
                    ) }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Lieu",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormTextField(
                        value = viewModel.location.value,
                        onValueChange = { newValue -> viewModel.onLocationChange(newValue) },
                        placeholder = "Description de la routine") }
                )
                FormField(
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Priorité",
                        modifier = Modifier.size(24.dp)
                    )},
                    isRequired = true,
                    field = { FormDropDownRadioField(
                        value = viewModel.priority.value.toString(),
                        onValueChange = { newValue -> viewModel.onPriorityChange(newValue) },
                        options = listOf("Haute","Moyenne","Basse"),
                        placeholder = "Priorité"
                    ) }
                )
            }
        }
    }
}