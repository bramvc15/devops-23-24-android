package com.example.templateapplication.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.templateapplication.models.Doctor
import com.example.templateapplication.models.DoctorViewModel
import com.example.templateapplication.navigation.Screens
import com.example.templateapplication.shared.AppPreferences
import com.example.templateapplication.shared.clickable


@Composable
fun DoctorSelectionScreen(navController : NavController) {
    var doctors by remember { mutableStateOf(emptyList<Doctor>()) }
    var view : DoctorViewModel

    val appPreferences = AppPreferences(LocalContext.current)


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(doctors) { doctor ->
            DoctorItem(doctor = doctor, onDoctorClick = {
                appPreferences.doctorId = doctor.id.toString()
                appPreferences.password = doctor.password
                navController.navigate(Screens.PasswordScreen.name)
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