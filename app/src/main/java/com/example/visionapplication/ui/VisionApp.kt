@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.visionapplication.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.visionapplication.navigation.AppNavigation
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.visionapplication.ui.utils.VisionNavigationType
import com.example.visionapplication.R
import com.example.visionapplication.navigation.Screens
import com.example.visionapplication.ui.components.NavigationDrawerContent
import com.example.visionapplication.ui.components.VisionBottomNavigation
import com.example.visionapplication.ui.components.VisionNavigationRail

/**
 * Vision app
 *
 * @param navigationType
 * @param navController
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisionApp(
    navigationType: VisionNavigationType,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreenTitle = try {
        val screen = backStackEntry?.destination?.route?.let { route ->
            Screens.values().find { it.name == route } ?: Screens.DoctorSelectionScreen
        } ?: Screens.DoctorSelectionScreen

        screen.name
    } catch (e: IllegalArgumentException) {
        Screens.DoctorSelectionScreen.name
    }
    Log.d("screen", "currentScreenTitle: $currentScreenTitle")
    when {
        currentScreenTitle == Screens.DoctorSelectionScreen.name -> {
            // DoctorSelectionScreen specific UI
            Scaffold(
                containerColor = Color.Transparent,
            ) { innerPadding ->
                AppNavigation(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                )
            }
        }
        navigationType == VisionNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            // Permanent Navigation Drawer UI
            Log.d("VisionApp", "VisionNavigationType.PERMANENT_NAVIGATION_DRAWER")
            PermanentNavigationDrawer(drawerContent = {
                PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
                    NavigationDrawerContent(
                        selectedDestination = navController.currentDestination,
                        navController = navController,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }) {
                Scaffold(
                    containerColor = Color.Transparent,
                ) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                    )
                }
            }
        }
        navigationType == VisionNavigationType.BOTTOM_NAVIGATION -> {
            // Bottom Navigation UI
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    VisionBottomNavigation(
                        selectedDestination = navController.currentDestination,
                        navController = navController,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

            ) { innerPadding ->

                AppNavigation(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,

                    )
            }
        }
        navigationType == VisionNavigationType.NAVIGATION_RAIL -> {
            // Navigation Rail UI
            Log.d("VisionApp", "VisionNavigationType.NAVIGATION_RAIL")
            Row {
                AnimatedVisibility(visible = true) {
                    NavigationRail() {
                        VisionNavigationRail(
                            selectedDestination = navController.currentDestination,
                            navController = navController,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                Scaffold(
                    containerColor = Color.Transparent,
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


