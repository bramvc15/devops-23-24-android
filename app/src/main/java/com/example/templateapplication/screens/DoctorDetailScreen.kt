package com.example.templateapplication.screens

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
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
import androidx.compose.ui.graphics.asImageBitmap
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


            val base64String = selectedDoctor.image
            val byteArray = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            Image(bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .clip(shape = CircleShape)
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

@Composable
fun DoctorInfoItem(title: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = value, fontSize = 16.sp)
    }
}
