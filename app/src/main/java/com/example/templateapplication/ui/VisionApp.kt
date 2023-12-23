@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.navigation.AppNavigation
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.navigation.listOfNavItems
import com.example.templateapplication.ui.utils.VisionContentType
import com.example.templateapplication.ui.utils.VisionNavigationType
import com.example.templateapplication.ui.views.DoctorViewModel
import com.example.templateapplication.R
import com.example.templateapplication.ui.components.NavigationDrawerContent
import com.example.templateapplication.ui.components.VisionNavigationRail
import com.example.templateapplication.ui.components.VisionNavigationRail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisionApp(
    navigationType: VisionNavigationType,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val contentType: VisionContentType = VisionContentType.LIST_ONLY
    val viewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory)
    val visionUiState = viewModel.uiState.collectAsState().value

    if (navigationType == VisionNavigationType.PERMANENT_NAVIGATION_DRAWER) {
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
    } else if (navigationType == VisionNavigationType.BOTTOM_NAVIGATION) {
        Log.d("VisionApp", "VisionNavigationType.BOTTOM_NAVIGATION")


    Scaffold(
            containerColor = Color.Transparent,
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
        ) { innerPadding ->

            AppNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController,

            )
        }
    }
    else {

        Row {
            AnimatedVisibility(visible = navigationType == VisionNavigationType.NAVIGATION_RAIL) {
                Log.d("VisionApp", "VisionNavigationType.NAVIGATION_RAIL")
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
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
                    modifier = Modifier.padding(innerPadding))
            }
        }
    }
}
