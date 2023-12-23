package com.example.visionapplication.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.visionapplication.navigation.listOfNavItems

/**
 * Vision navigation rail
 *
 * @param selectedDestination
 * @param modifier
 * @param navController
 */
@Composable
fun VisionNavigationRail(
    selectedDestination: NavDestination?,
    modifier: Modifier = Modifier,
    navController: NavHostController,

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
                Icon(
                    imageVector = navItem.icon,
                    contentDescription = navItem.label,
                )
            },
        )
    }


}