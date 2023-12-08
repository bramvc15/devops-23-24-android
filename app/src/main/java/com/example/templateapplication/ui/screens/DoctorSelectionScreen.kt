package com.example.templateapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.shared.AppPreferences
import com.example.templateapplication.ui.views.DoctorViewModel


@Composable
fun DoctorSelectionScreen(
    doctorViewModel: DoctorViewModel = viewModel(),
    onNextButtonClicked: (Doctor) -> Unit,
    ) {

    val appPreferences = AppPreferences(LocalContext.current)

    val doctors by doctorViewModel.doctors.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(doctors) { doctor ->
            DoctorItem(doctor = doctor) {
                doctorViewModel.selectDoctor(doctor) // Save the chosen doctor in the ViewModel
                onNextButtonClicked(doctor)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
private fun DoctorItem(doctor : Doctor, onNextButtonClicked: (Doctor) -> Unit) {
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