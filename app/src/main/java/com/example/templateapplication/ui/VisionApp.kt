@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.templateapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.navigation.AppNavigation
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import com.example.templateapplication.ui.screens.CalendarWeekScreen
import com.example.templateapplication.ui.screens.CalenderMonthScreen
import com.example.templateapplication.ui.screens.DoctorSelectionScreen
import com.example.templateapplication.ui.screens.PasswordScreen
import com.example.templateapplication.ui.screens.CalendarWeekScreen
import com.example.templateapplication.ui.screens.CalenderMonthScreen
import com.example.templateapplication.ui.utils.VisionContentType
import com.example.templateapplication.ui.utils.VisionNavigationType
import com.example.templateapplication.ui.views.DoctorViewModel
import com.example.templateapplication.R
import com.example.templateapplication.ui.components.NavigationDrawerContent
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
   /* val goHome: () -> Unit = {
        navController.popBackStack(
            DoctorSelectionScreen.Start.name,
            inclusive = false,
        )
    }
    val goToMainLogin= { navController.navigate(PasswordScreen.Detail.name) { launchSingleTop = true } }
    val goToCalendarWeek = { navController.navigate(CalendarWeekScreen.About.name)  {launchSingleTop = true} }
    val goToCalendarMonth = { navController.navigate(CalenderMonthScreen.Camera.name) {launchSingleTop = true} }

    val currentScreenTitle = TaskOverviewScreen.valueOf(
        backStackEntry?.destination?.route ?: TaskOverviewScreen.Start.name,
    ).title*/
    if (navigationType == VisionNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            for (navItem in listOfNavItems) {
                NavigationDrawerContent(
                    selectedDestination = navController.currentDestination,
                    navController = navController,
                    onTabPressed = { navController.navigate(navItem.route) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

    }) {
            Scaffold(
                containerColor = Color.Transparent,

        // modifier = Modifier.padding(dimensionResource(id = R.dimen.drawer_width), 0.dp, 0.dp, 0.dp )
        ) { innerPadding ->

                AppNavigation(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                )
            }

    }
    }else if (navigationType == VisionNavigationType.BOTTOM_NAVIGATION) {
        Scaffold(
            containerColor = Color.Transparent,
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
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                VisionNavigationRail(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = { node: String -> navController.navigate(node) },
                )
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
