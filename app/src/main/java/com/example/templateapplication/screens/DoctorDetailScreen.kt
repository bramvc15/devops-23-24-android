package com.example.templateapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.ImagePainter
import com.example.templateapplication.models.Doctor

@Composable
fun DoctorDetailScreen(selectedDoctor: Doctor) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {

            selectedDoctor.image.takeIf { it.isNotBlank() }?.let { imageUrl ->
                AsyncImage(
                    model = selectedDoctor.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp) // Adjust the size as needed
                        .clip(shape = CircleShape) // Optionally round the image
                )

            Text(
                text = "${selectedDoctor.name}",
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 16.dp),
            )

            DoctorInfoItem("Name", selectedDoctor.name)
            DoctorInfoItem("Gender", selectedDoctor.gender)
            DoctorInfoItem("Specialization", selectedDoctor.specialization)
            DoctorInfoItem("About", selectedDoctor.infoOver)
            DoctorInfoItem("Education", selectedDoctor.infoOpleiding)
            DoctorInfoItem("Publications", selectedDoctor.infoPublicaties)

            Spacer(modifier = Modifier.height(16.dp))


            }
        }
    }
}

@Composable
fun DoctorInfoItem(title: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = value, fontSize = 16.sp)
    }
}
