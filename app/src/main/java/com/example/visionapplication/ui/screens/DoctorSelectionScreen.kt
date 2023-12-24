package com.example.visionapplication.ui.screens

import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.visionapplication.model.Doctor
import com.example.visionapplication.ui.views.DoctorViewModel
import com.example.visionapplication.ui.views.VisionUiState


/**
 * Doctor selection screen
 *
 * @param doctorViewModel
 * @param onNextButtonClicked
 * @receiver
 */
@Composable
fun DoctorSelectionScreen(
    doctorViewModel: DoctorViewModel = viewModel(factory = DoctorViewModel.Factory),
    onNextButtonClicked: (Doctor) -> Unit,

) {
    val doctors by doctorViewModel.doctors.collectAsState()
    val visionUiState by doctorViewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(doctors) { doctor ->
            Log.d("DoctorSelectionScreen", "Doctor: $doctor")
            DoctorItem(doctor = doctor, modifier = Modifier.fillMaxWidth(), viewModel = doctorViewModel, visionUiState = visionUiState, onNextButtonClicked = onNextButtonClicked)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
private fun DoctorItem(doctor : Doctor, modifier: Modifier = Modifier, viewModel: DoctorViewModel, visionUiState: VisionUiState, onNextButtonClicked: (Doctor) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onNextButtonClicked(doctor)
                viewModel.selectDoctor(doctor)
            },
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
                modifier = Modifier.testTag(doctor.name),
                text = doctor.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }
    }
}