package com.example.templateapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.ui.graphics.vector.ImageVector
data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Notities",
        icon = Icons.Default.Create,
        route = Screens.NoteScreen.name
    ),
    NavItem(
        label = "Week",
        icon = Icons.Outlined.DateRange,
        route = Screens.CalenderWeekScreen.name
    ),
    NavItem(
        label = "Maand",
        icon = Icons.Filled.DateRange,
        route = Screens.CalenderMonthScreen.name
    ),

)