package com.example.templateapplication.navigation

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
import com.example.templateapplication.ui.screens.NoteScreen
import com.example.templateapplication.ui.screens.PasswordScreen

@Composable
fun AppNavigation() {

    val navController: NavHostController = rememberNavController()

    Scaffold(

    ) {
            paddingValues ->

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
                DoctorSelectionScreen(onNextButtonClicked = {
                    navController.navigate(Screens.PasswordScreen.name)
                })
            }
            composable(
                route = Screens.PasswordScreen.name
            ){
                PasswordScreen()
            }

        }
    }
}
