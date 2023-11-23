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
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Doctors",
        icon = Icons.Default.Person,
        route = Screens.DoctorsScreen.name
    ),
    NavItem(
        label = "CAl2",
        icon = Icons.Default.Person,
        route = Screens.Calender.name
    ),
    NavItem(
        label = "Calender",
        icon = Icons.Default.Close,
        route = Screens.CalenderScreen.name
    )
)