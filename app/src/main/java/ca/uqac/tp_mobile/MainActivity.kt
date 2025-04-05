package ca.uqac.tp_mobile

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    // Enregistrer le callback pour le résultat de la demande de permission
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(null, "Permission accordée")
        } else {
            Log.d(null, "Permission refusée")
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                // Cas 1: Permission déjà accordée
                ContextCompat.checkSelfPermission(
                    this, POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission déjà accordée
                }
                // Cas 2: L'utilisateur a déjà refusé, montrer explication
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, POST_NOTIFICATIONS
                ) -> {
                    // Afficher un dialogue expliquant pourquoi
                    requestPermissionLauncher.launch(
                        POST_NOTIFICATIONS
                    )
                }
                // Cas 3: Première demande ou "Ne plus demander" non coché
                else -> {
                    // Demander la permission directement
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
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
                                routineId = routineId, navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}