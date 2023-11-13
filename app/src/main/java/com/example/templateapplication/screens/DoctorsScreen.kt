package com.example.templateapplication.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.templateapplication.models.Doctor
import com.example.templateapplication.models.DoctorViewModel
import com.example.templateapplication.navigation.Screens
import java.util.Base64

@Composable
fun DoctorsScreen(navController : NavHostController) {

    val viewModel: DoctorViewModel = viewModel()

    LaunchedEffect(true) {
        viewModel.fetchDoctors()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                      navController.navigate(Screens.AddDoctorScreen.name)
            },
            //modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add More",

            )
        }
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
                DoctorCard(doctor = doctor, navController = navController)
            }
        }

    }
}


@Composable
fun DoctorCard(doctor: Doctor, navController : NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                ,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = doctor.image,

                contentDescription = null,
                        modifier = Modifier
                            .size(160.dp) // Adjust the size as needed
                            .clip(shape = CircleShape) // Optionally round the image
            )
            Log.d("mieuw","${doctor.image}")
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
            Log.d("somebody", doctor.gender)
            Text(
                text = "Expertise: ${doctor.gender}",

                fontSize = 16.sp,
                color = Color.Gray
            )

            Button(
                onClick = {
                    try {
                        navController.navigate( "${Screens.DoctorDetailScreen.name}/${doctor.id}")
                    } catch (e: Exception) {


                        Log.e("boom", e.message!!)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "See More")
            }
        }
    }
}


