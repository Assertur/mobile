package ca.uqac.tp_mobile.presentation.routineDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
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
import ca.uqac.tp_mobile.presentation.formatDaysForLongDisplay
import ca.uqac.tp_mobile.utils.getRoutineById
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RoutineDetailsScreen(
    routineId: Int,
    navController: NavController,
    viewModel: RoutineDetailsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(routineId) {
        val routine = getRoutineById(routineId)
        routine.let { viewModel.setSelectedRoutine(it) }
    }
    val selectedRoutine by viewModel.selectedRoutine.collectAsState()

    val scrollState = rememberScrollState()

    val primaryColor = Color(0xFF000547)
    val secondaryColor = Color(0xFFF4F4FB)
    selectedRoutine?.let { routine: RoutineVM ->
        // TODO ? : utiliser la topbox du scaffold pour y mettre le bouton
        Scaffold(
            containerColor = primaryColor,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
            BottomActionBarWithModification(onDeleteWithNavigation = {
                viewModel.onEvent(DetailsRoutineEvent.Delete)
            }, onEdit = {
                navController.navigate(Screen.AddEditRoutine.route + "?routineId=${routineId}")
            })
        }) { outerPadding ->
            LaunchedEffect(true) {
                viewModel.eventFlow.collectLatest { event ->
                    if (event is DetailsRoutineUiEvent.Delete) {
                        navController.navigate(Screen.ListRoutineScreen.route)
                    } else if (event is DetailsRoutineUiEvent.ShowMessage) {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
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
                        contentDescription = "Retour", tint = secondaryColor
                    )
                }

                Text(
                    text = "Voir une routine",
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 16.dp),
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
                            },
                            titleText = "Titre",
                            contentText = routine.title
                        )

                        RoutinePresentationField(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_subtitles),
                                    contentDescription = "Description",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                            },
                            titleText = "Description",
                            contentText = routine.description
                        )

                        RoutinePresentationField(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_access_time),
                                    contentDescription = "Heure",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                            },
                            titleText = "Heure",
                            contentText = routine.hour
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
                            },
                            titleText = "Lieu",
                            contentText = routine.location
                        )
                        // TODO : exporter ça dans un composant propre ?
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(horizontal = 25.dp)
                                    .fillMaxWidth()
                            ) {
                                Spacer(
                                    modifier = Modifier.padding(
                                        horizontal = 15.dp,
                                        vertical = 40.dp
                                    )
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_priority_high),
                                    contentDescription = "Priorité",
                                    modifier = Modifier.size(24.dp),
                                    tint = primaryColor
                                )
                                Text(
                                    text = "Priorité",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF000547)
                                )
                            }
                            // FIXME : aligner le texte verticalement
                            Box(Modifier.background(routine.priority.type.backgroundColor)) {
                                Text(
                                    text = routine.priority.label,
                                    fontSize = 18.sp,
                                    color = Color(0xFF000547),
                                    modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
                                )
                            }
                        }
                    }

                }
            }

        }
    } ?: run {
        Text("Routine non trouvée")
    }

}