package com.example.templateapplication.navigation

import android.net.Uri
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.component.BackConfirmationDialog
import com.example.templateapplication.ui.screens.CalendarWeekScreen
import com.example.templateapplication.ui.screens.CalenderMonthScreen
import com.example.templateapplication.ui.screens.DoctorSelectionScreen
import com.example.templateapplication.ui.screens.PasswordScreen
import com.example.templateapplication.ui.screens.notes.NoteScreen
import com.example.templateapplication.ui.views.DoctorViewModel
import com.example.templateapplication.ui.views.NoteViewModel

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val doctorViewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory)
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)

    Scaffold(
        bottomBar = {
                GetNavigationBar(navController = navController)
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screens.DoctorSelectionScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = Screens.NoteScreen.name) {
                LocalOnBackPressedDispatcherOwner.current?.let { it1 ->
                    BackConfirmationDialog(
                        onBackPressedDispatcher = it1.onBackPressedDispatcher,
                        onConfirmed = {
                            navController.navigate(Screens.DoctorSelectionScreen.name)
                        },
                        onCancel = {

                        }
                    )
                }
                NoteScreen(noteViewModel = noteViewModel

                )
            }
            composable(route = Screens.CalenderMonthScreen.name) {
                LocalOnBackPressedDispatcherOwner.current?.let { it1 ->
                    BackConfirmationDialog(
                        onBackPressedDispatcher = it1.onBackPressedDispatcher,
                        onConfirmed = {
                            navController.navigate(Screens.DoctorSelectionScreen.name)
                        },
                        onCancel = {

                        }
                    )
                }
                CalenderMonthScreen()
            }

            composable(route = Screens.CalenderWeekScreen.name) {
                LocalOnBackPressedDispatcherOwner.current?.let { it1 ->
                    BackConfirmationDialog(
                        onBackPressedDispatcher = it1.onBackPressedDispatcher,
                        onConfirmed = {
                            navController.navigate(Screens.DoctorSelectionScreen.name)
                        },
                        onCancel = {

                        }
                    )
                }
                CalendarWeekScreen()
            }

            composable(route = Screens.DoctorSelectionScreen.name) {

                DoctorSelectionScreen(
                    onNextButtonClicked = { doctor ->

                        doctorViewModel.selectDoctor(doctor)

                        navController.navigate(
                            "${Screens.PasswordScreen.name}/${Uri.encode(doctor.name)}/${
                                Uri.encode(
                                    doctor.imageLink
                                )
                            }"
                        )
                    }
                )
            }

            composable(route = Screens.PasswordScreen.name + "/{doctorName}/{doctorImage}") { backStackEntry ->
                val doctorName = backStackEntry.arguments?.getString("doctorName") ?: ""
                val doctorImage = backStackEntry.arguments?.getString("doctorImage") ?: ""
                PasswordScreen(
                    doctorName = Uri.decode(doctorName),
                    doctorImage = Uri.decode(doctorImage),
                    onNextButtonClicked = {
                        navController.navigate(Screens.CalenderWeekScreen.name)
                    }
                )
            }
        }
    }

}

@Composable
fun GetNavigationBar(navController: NavHostController) {
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