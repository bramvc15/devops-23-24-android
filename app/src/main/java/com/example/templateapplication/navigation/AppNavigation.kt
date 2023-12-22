package com.example.templateapplication.navigation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.templateapplication.component.BackConfirmationDialog
import com.example.templateapplication.ui.screens.DoctorSelectionScreen
import com.example.templateapplication.ui.screens.calendarmonth.CalenderMonthScreen
import com.example.templateapplication.ui.screens.calendarweek.CalendarWeekScreen
import com.example.templateapplication.ui.screens.notes.NoteScreen
import com.example.templateapplication.ui.views.DoctorViewModel
import com.example.templateapplication.ui.views.NoteViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val doctorViewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory)
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)
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
            CalendarWeekScreen(doctorViewModel)
        }
        composable(route = Screens.DoctorSelectionScreen.name) {
            DoctorSelectionScreen(
                onNextButtonClicked = { doctor ->
                    Log.d(doctor.name, "Doctor: $doctor")
                    navController.navigate(
                        Screens.CalenderWeekScreen.name
                    )

                }
            )
        }
    }

}