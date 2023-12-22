package com.example.templateapplication.navigation

import android.net.Uri
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.example.templateapplication.ui.screens.NoteScreen
import com.example.templateapplication.ui.screens.PasswordScreen
import com.example.templateapplication.ui.utils.VisionNavigationType
import com.example.templateapplication.ui.views.DoctorViewModel

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.dp
import com.example.templateapplication.ui.views.VisionUiState
import androidx.compose.foundation.layout.PaddingValues
import com.example.templateapplication.ui.utils.VisionContentType


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.DoctorSelectionScreen.name,
        modifier = modifier
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
                NoteScreen()
            }
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
                CalenderMonthScreen()
            }
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
                CalendarWeekScreen()
            }
        }
        composable(route = Screens.DoctorSelectionScreen.name) {
            DoctorSelectionScreen(
                onNextButtonClicked = { doctor ->



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