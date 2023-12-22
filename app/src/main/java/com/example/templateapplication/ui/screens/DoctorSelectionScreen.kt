package com.example.templateapplication.ui.screens

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.ui.views.DoctorViewModel
import com.example.templateapplication.ui.views.VisionUiState


@Composable
fun DoctorSelectionScreen(
    doctorViewModel: DoctorViewModel,
    onNextButtonClicked: (Doctor) -> Unit,
    visionUiState: VisionUiState
) {

    val doctors by doctorViewModel.doctors.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(doctors) { doctor ->
            Log.d("DoctorSelectionScreen", "Doctor: $doctor")
            DoctorItem(doctor = doctor, onNextButtonClicked, visionUiState, modifier = Modifier.fillMaxWidth(), viewModel = doctorViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
private fun DoctorItem(doctor : Doctor, onNextButtonClicked: (Doctor) -> Unit, visionUiState: VisionUiState, modifier: Modifier = Modifier, viewModel: DoctorViewModel) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onNextButtonClicked(doctor)
                // Update VisionUiState with the selected doctor
                // (Assuming VisionUiState has a property selectedDoctor)
                val updatedUiState = visionUiState.copy(selectedDoctor = doctor)
                viewModel.updateUiState(updatedUiState)
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