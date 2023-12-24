package com.example.visionapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Nav item
 *
 * @property label
 * @property icon
 * @property route
 * @constructor Create empty Nav item
 */
data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Notes",
        icon = Icons.Default.Create,
        route = Screens.NoteScreen.name
    ),
    NavItem(
        label = "Week",
        icon = Icons.Outlined.DateRange,
        route = Screens.CalenderWeekScreen.name
    ),
    NavItem(
        label = "Month",
        icon = Icons.Filled.DateRange,
        route = Screens.CalenderMonthScreen.name
    ),

)