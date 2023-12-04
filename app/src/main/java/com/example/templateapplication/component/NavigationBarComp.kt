package com.example.templateapplication.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.navigation.Screens
import com.example.templateapplication.navigation.listOfNavItems
import com.example.templateapplication.ui.screens.CalendarWeekScreen
import com.example.templateapplication.ui.screens.CalenderMonthScreen
import com.example.templateapplication.ui.screens.NoteScreen
import com.example.templateapplication.ui.views.DoctorViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationBarComp(doctorViewModel: DoctorViewModel) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
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
    }){

                paddingValues ->

            NavHost(
                navController = navController,
                startDestination = Screens.NoteScreen.name,
                modifier = androidx.compose.ui.Modifier
                    .padding(paddingValues)
            ) {
                composable(route = Screens.NoteScreen.name) {
                    NoteScreen()
                }
                composable(route = Screens.CalenderMonthScreen.name) {
                    CalenderMonthScreen()
                }

                composable(route = Screens.CalenderWeekScreen.name) {
                    CalendarWeekScreen(doctorViewModel = doctorViewModel)
                }
            }
    }
}

