package com.example.templateapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
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
        label = "Blogs",
        icon = Icons.Default.Person,
        route = Screens.BlogsScreen.name
)
)