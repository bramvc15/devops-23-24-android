package com.example.templateapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.navigation.listOfNavItems
import com.example.templateapplication.ui.views.DoctorViewModel
import com.example.templateapplication.ui.views.VisionUiState


@Composable
fun DoctorSelectionScreen(
    doctorViewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory),
    onNextButtonClicked: (Doctor) -> Unit,
    modifier: Modifier = Modifier,
    visionUiState: VisionUiState,
) {
    DoctorSelectionContent(
        doctors = doctors,
        onNextButtonClicked = onNextButtonClicked,
        visionUiState = visionUiState,
        modifier = modifier
    )

}

@Composable
fun DoctorSelectionContent(
    doctors: List<Doctor>,
    onNextButtonClicked: (Doctor) -> Unit,
    visionUiState: VisionUiState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        VisionNavigationRail(
            navController = navController, // Added missing navController parameter
            visionUiState = visionUiState,
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn {
            items(doctors) { doctor ->
                DoctorItem(
                    doctor = doctor,
                    onNextButtonClicked = onNextButtonClicked
                )
            }
        }
    }
}

@Composable
private fun DoctorItem(doctor: Doctor, onNextButtonClicked: (Doctor) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onNextButtonClicked(doctor)
            },
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val painter = rememberImagePainter(
                data = doctor.imageLink,
                builder = {
                    transformations(CircleCropTransformation())
                }
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth()
                    .clip(CircleShape),
                contentScale = ContentScale.Inside,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = doctor.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun VisionNavigationRail(navController: NavHostController, visionUiState: VisionUiState, modifier: Modifier = Modifier) {
    NavigationRail(modifier = modifier) {
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
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                }
            )
        }
    }
}

@Composable
fun VisionBottomNavigationBar(navController: NavHostController) {
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
                    androidx.compose.material3.Text(text = navItem.label)
                }
            )
        }
    }
}