package com.example.templateapplication.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.example.templateapplication.navigation.listOfNavItems

@Composable
fun VisionNavigationRail(
    selectedDestination: NavDestination?,
    onTabPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in listOfNavItems) {
            NavigationRailItem(
                selected = selectedDestination?.route == navItem.label,
                onClick = { onTabPressed(navItem.label) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                    )
                },
            )
        }
    }
}