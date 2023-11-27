package com.example.templateapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.navigation.Screens
import com.example.templateapplication.shared.AppPreferences


@Composable
fun DoctorSelectionScreen(doctors : List<Doctor>) {

    val appPreferences = AppPreferences(LocalContext.current)
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(doctors) { doctor ->
            DoctorItem(doctor = doctor, onDoctorClick = {
                appPreferences.doctorId = doctor.id.toString()
                navController.navigate(Screens.PasswordScreen.name){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
private fun DoctorItem(doctor: Doctor, onDoctorClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onDoctorClick() },
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = doctor.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


}
