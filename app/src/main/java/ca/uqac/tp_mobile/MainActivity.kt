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
import androidx.room.Room
import ca.uqac.tp_mobile.data.RoutineDatabase
import ca.uqac.tp_mobile.navigation.Screen
import ca.uqac.tp_mobile.presentation.addEdit.AddEditRoutineViewModel
import ca.uqac.tp_mobile.presentation.addEdit.AddEditStoryScreen
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineScreen
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineViewModel
import ca.uqac.tp_mobile.presentation.routineDetails.RoutineDetailsScreen
import ca.uqac.tp_mobile.presentation.routineDetails.RoutineDetailsViewModel
import ca.uqac.tp_mobile.ui.theme.TP_MobileTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoutineDatabase::class.java,
            RoutineDatabase.DATABASE_NAME
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP_MobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListRoutineScreen.route,
                        modifier = Modifier.padding(innerPadding)

                    ) {
                        composable(Screen.ListRoutineScreen.route) {
                            val routines = viewModel<ListRoutineViewModel>{
                                ListRoutineViewModel(db.dao)
                            }
                            ListRoutineScreen(
                                routines,
                                navController
                            )
                        }
                        composable(
                            route = Screen.AddEditRoutine.route + "?routineId={routineId}",
                            arguments = listOf(navArgument("routineId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) { backStackEntry ->
                            val routineId = backStackEntry.arguments?.getInt("routineId") ?: -1
                            val routine = viewModel<AddEditRoutineViewModel> {
                                AddEditRoutineViewModel(db.dao, routineId)
                            }
                            AddEditStoryScreen(routine, navController)
                        }
                        composable(
                            route = Screen.RoutineDetails.route,
                            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val routineId = backStackEntry.arguments?.getInt("routineId") ?: -1
                            val routine = viewModel<RoutineDetailsViewModel>{
                                RoutineDetailsViewModel(db.dao, routineId)
                            }
                            RoutineDetailsScreen(
                                routineId = routineId,
                                viewModel = routine,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}