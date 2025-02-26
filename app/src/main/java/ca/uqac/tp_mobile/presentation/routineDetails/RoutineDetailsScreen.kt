package ca.uqac.tp_mobile.presentation.routineDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineViewModel

@Composable
fun RoutineDetailsScreen(routineId: Int, viewModel: RoutineDetailsViewModel, listRoutineViewModel: ListRoutineViewModel, navController: NavController) {
    LaunchedEffect(routineId) {
        val routine = listRoutineViewModel.getRoutineById(routineId)
        routine?.let { viewModel.setSelectedRoutine(it) }
    }
    val selectedRoutine by viewModel.selectedRoutine.collectAsState()

    val primaryColor = Color(0xFF000547)
    val secondaryColor = Color(0xFFF4F4FB)
    selectedRoutine?.let { routine : RoutineVM ->
        Scaffold(containerColor = primaryColor) { outerPadding ->
            Text(
                text = "Voir une routine",
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 16.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    color = secondaryColor
                )
            )
            Scaffold(Modifier.padding(outerPadding).padding(50.dp).clip(RoundedCornerShape(32.dp)), containerColor = secondaryColor) { innerPadding ->
                Text(routine.title, color = Color.White, modifier = Modifier.padding(innerPadding))
            }
        }
    } ?: run {
        Text("Routine non trouvée")
    }

}