package com.example.templateapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.NoteScreen.name
    ),
    NavItem(
        label = "CAl2",
        icon = Icons.Default.Person,
        route = Screens.CalenderWeekScreen.name
    ),
    NavItem(
        label = "Calender",
        icon = Icons.Default.Close,
        route = Screens.CalenderMonthScreen.name
    ),
    /*
    NavItem(
        label = "test",
        icon = Icons.Default.Close,
        route = Screens.DoctorSelectionScreen.name
    ),
    NavItem(
        label = "test2",
        icon = Icons.Default.Close,
        route = Screens.PasswordScreen.name
    )
    */

)