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
import com.example.templateapplication.ui.screens.NoteScreen
import com.example.templateapplication.ui.screens.PasswordScreen
import com.example.templateapplication.ui.utils.VisionNavigationType
import com.example.templateapplication.ui.views.DoctorViewModel
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigation(navigationType: VisionNavigationType,
                  modifier: Modifier = Modifier){
    val navController: NavHostController = rememberNavController()
    val doctorViewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory)

    Scaffold(
        bottomBar = {
            GetNavigationBar(navController = navController, navigationType)
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
                NoteScreen()
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
fun GetNavigationBar(navController: NavHostController, navigationType: VisionNavigationType) {
    when (navigationType) {
        VisionNavigationType.BOTTOM_NAVIGATION -> {
            VisionBottomNavigationBar(navController = navController)
        }
        VisionNavigationType.NAVIGATION_RAIL -> {
            VisionNavigationRail(navController = navController)
        }
        VisionNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            VisionPermanentNavigationDrawer(navController = navController)
        }
    }

}

@Composable
fun VisionPermanentNavigationDrawer(navController: NavHostController) {
    PermanentDrawerSheet(
        modifier = Modifier.fillMaxWidth()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        listOfNavItems.forEach { navItem ->
            NavigationRailItem(
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
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                }
            )
        }
    }
}


@Composable
fun VisionNavigationRail(navController: NavHostController) {
    NavigationRail() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        listOfNavItems.forEach { navItem ->
            NavigationRailItem(
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
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
        }

            )
        }
    }

}

@Composable
fun VisionBottomNavigationBar(navController: NavHostController) {
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
