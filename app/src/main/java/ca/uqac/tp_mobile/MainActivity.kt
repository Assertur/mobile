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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.uqac.tp_mobile.navigation.Screen
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineScreen
import ca.uqac.tp_mobile.presentation.listRoutine.ListRoutineViewModel
import ca.uqac.tp_mobile.ui.theme.TP_MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP_MobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stories = viewModel<ListRoutineViewModel>()
                    //val addEditStories = viewModel<AddEditStoryViewModel>()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListRoutineScreen.route,
                        modifier = Modifier.padding(innerPadding)

                    ){
                        composable(Screen.ListRoutineScreen.route){ ListRoutineScreen(stories, navController) }
                        //composable(Screen.AddEditStoryScreen.route){ AddEditStoryScreen(navController,addEditStories)}
                    }
                }
            }
        }
    }
}