package ca.uqac.tp_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.uqac.tp_mobile.navigation.Screen
import ca.uqac.tp_mobile.presentation.addEdit.AddEditRoutineScreen
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineScreen
import ca.uqac.tp_mobile.presentation.routineDetails.RoutineDetailsScreen
import ca.uqac.tp_mobile.ui.theme.TP_MobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                            ListRoutineScreen(navController)
                        }
                        composable(
                            route = Screen.AddEditRoutine.route + "?routineId={routineId}",
                            arguments = listOf(navArgument("routineId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) { backStackEntry ->
                            AddEditRoutineScreen(navController)
                        }
                        composable(
                            route = Screen.RoutineDetails.route,
                            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val routineId = backStackEntry.arguments?.getInt("routineId") ?: -1
                            RoutineDetailsScreen(
                                routineId = routineId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}