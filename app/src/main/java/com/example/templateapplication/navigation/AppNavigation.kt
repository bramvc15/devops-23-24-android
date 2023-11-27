package com.example.templateapplication.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.ui.screens.CalendarWeekScreen
import com.example.templateapplication.ui.screens.CalenderMonthScreen
import com.example.templateapplication.ui.screens.DoctorSelectionScreen
import com.example.templateapplication.ui.screens.ErrorScreen
import com.example.templateapplication.ui.screens.LoadingScreen
import com.example.templateapplication.ui.screens.NoteScreen
import com.example.templateapplication.ui.screens.PasswordScreen
import com.example.templateapplication.ui.views.DoctorUiState

@Composable
fun AppNavigation(doctorUiState: DoctorUiState, modifier :Modifier) {

    val navController: NavHostController = rememberNavController()

    // Scaffold with NavigationBar
    Scaffold(
        /*
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }

         */
    ) {
            paddingValues ->
        // NavHost for screens with bottom bar
        NavHost(
            navController = navController,
            startDestination = Screens.DoctorSelectionScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.NoteScreen.name) {
                NoteScreen()
            }
            composable(route = Screens.CalenderMonthScreen.name) {
                CalenderMonthScreen()
            }

            composable(route = Screens.CalenderWeekScreen.name) {
                CalendarWeekScreen()
            }

            composable(route = Screens.DoctorSelectionScreen.name) {

                when (doctorUiState) {
                    is DoctorUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
                    is DoctorUiState.Success -> DoctorSelectionScreen(
                        doctorUiState.doctors
                    )
                    is DoctorUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
                }

            }
            composable(route = Screens.PasswordScreen.name) {
                PasswordScreen()
            }

        }
    }
}
