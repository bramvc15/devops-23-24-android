package com.example.templateapplication.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.data.DoctorService
import com.example.templateapplication.models.Doctor
import com.example.templateapplication.models.DoctorViewModel

@Composable
fun DoctorsScreen() {

    val viewModel: DoctorViewModel = viewModel()

    //val doctes = DoctorService.getDoctors()
    LaunchedEffect(true) {
        viewModel.fetchDoctors()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Doctors",
            fontFamily = FontFamily.Serif,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.doctors) { doctor ->
                DoctorCard(doctor = doctor)
                Log.d("jdfkgljsdklgjsdkl", "gjskldfjgklsdf")
            }
        }
    }
}


@Composable
fun DoctorCard(doctor: Doctor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.oog),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp) // Adjust the size as needed
                    .clip(shape = CircleShape) // Optionally round the image
            )

            Text(
                text = doctor.name,
                fontSize = 20.sp,
                color = Color.Black
            )

            Text(
                text = "Expertise: ${doctor.specialization}",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "Expertise: ${doctor.gender}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Button(
                onClick = { /* Handle See More button click */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "See More")
            }
        }
    }
}


