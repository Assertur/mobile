package ca.uqac.tp_mobile.presentation.routineDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.presentation.components.BottomActionBarWithModification
import ca.uqac.tp_mobile.presentation.components.RoutinePresentationField
import ca.uqac.tp_mobile.presentation.components.RoutinePresentationPriorityField
import ca.uqac.tp_mobile.presentation.formatDaysForLongDisplay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RoutineDetailsScreen(
    routineId: Int,
    navController: NavController,
    viewModel: RoutineDetailsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedRoutine by viewModel.selectedRoutine.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DetailsRoutineUiEvent.Delete -> {
                    navController.navigate(Screen.ListRoutineScreen.route)
                }

                is DetailsRoutineUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    LaunchedEffect(routineId) {
        viewModel.fetchRoutineById(routineId)
    }

    val scrollState = rememberScrollState()

    val primaryColor = Color(0xFF000547)
    val secondaryColor = Color(0xFFF4F4FB)

    selectedRoutine?.let { routine: RoutineVM ->
        Scaffold(containerColor = primaryColor,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                BottomActionBarWithModification(onDeleteWithNavigation = {
                    viewModel.onEvent(DetailsRoutineEvent.Delete)
                }, onEdit = {
                    navController.navigate(Screen.AddEditRoutine.route + "?routineId=${routineId}")
                })
            }) { outerPadding ->
            Column(
                Modifier
                    .padding(outerPadding)
                    .padding(horizontal = 25.dp)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = { navController.navigate(Screen.ListRoutineScreen.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00141F)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = secondaryColor
                    )
                }

                Text(
                    text = "Voir une routine",
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 16.dp),
                    style = TextStyle(
                        fontSize = 36.sp,
                        textAlign = TextAlign.Center,
                        color = secondaryColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Scaffold(
                    Modifier
                        .padding(outerPadding)
                        .padding(horizontal = 25.dp)
                        .clip(RoundedCornerShape(32.dp)), containerColor = secondaryColor
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(scrollState)
                    ) {
                        RoutinePresentationField(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_title),
                                    contentDescription = "Titre",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                            }, titleText = "Titre", contentText = routine.title
                        )

                        RoutinePresentationField(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_subtitles),
                                    contentDescription = "Description",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                            }, titleText = "Description", contentText = routine.description
                        )

                        RoutinePresentationField(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_access_time),
                                    contentDescription = "Heure",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                            }, titleText = "Heure", contentText = routine.hour
                        )

                        RoutinePresentationField(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_calendar_today),
                                    contentDescription = "Jour(s)",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                            },
                            titleText = "Jour(s)",
                            contentText = routine.day.formatDaysForLongDisplay()
                        )

                        RoutinePresentationField(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_location_on),
                                    contentDescription = "Lieu",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                            }, titleText = "Lieu", contentText = routine.locationName
                        )
                        RoutinePresentationPriorityField(priority = routine.priority)
                    }
                }
            }
        }
    } ?: run {
        Text("Routine non trouvée")
    }

}