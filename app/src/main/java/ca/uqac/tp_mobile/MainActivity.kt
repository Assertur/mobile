package ca.uqac.tp_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.uqac.tp_mobile.navigation.Screen
import ca.uqac.tp_mobile.presentation.formAdd.FormAddRoutineScreen
import ca.uqac.tp_mobile.presentation.formAdd.FormAddRoutineViewModel
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineScreen
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineViewModel
import ca.uqac.tp_mobile.presentation.routineDetails.RoutineDetailsScreen
import ca.uqac.tp_mobile.presentation.routineDetails.RoutineDetailsViewModel
import ca.uqac.tp_mobile.ui.theme.TP_MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP_MobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stories = viewModel<ListRoutineViewModel>()
                    val addStories = viewModel<FormAddRoutineViewModel>()
                    val routine = viewModel<RoutineDetailsViewModel>()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListRoutineScreen.route,
                        modifier = Modifier.padding(innerPadding)

                    ){
                        composable(Screen.ListRoutineScreen.route){ ListRoutineScreen(stories, navController) }
                        composable(Screen.FormAddRoutine.route){ FormAddRoutineScreen(addStories,navController) }
                        composable(
                            route = Screen.RoutineDetails.route,
                            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val routineId = backStackEntry.arguments?.getInt("routineId") ?: -1
                            RoutineDetailsScreen(routineId = routineId, viewModel = routine, listRoutineViewModel = stories, navController = navController)
                        }                    }
                }
            }
        }
    }
}