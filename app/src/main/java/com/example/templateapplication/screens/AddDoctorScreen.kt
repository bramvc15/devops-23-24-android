package com.example.templateapplication.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.templateapplication.R
import com.example.templateapplication.models.Doctor
import com.example.templateapplication.models.DoctorViewModel

@Composable
fun AddDoctorScreen() {
    var doctorName by remember { mutableStateOf("") }
    var doctorSpecialization by remember { mutableStateOf("") }
    var doctorGender by remember { mutableStateOf("") }
    var doctorInfoOver by remember { mutableStateOf("") }
    var doctorImage by remember { mutableStateOf("") }
    var doctorInfoOpleiding by remember { mutableStateOf("") }
    var doctorInfoPublicaties by remember { mutableStateOf("")}
    // Add more fields as needed for the doctor details
    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("Select Gender") }
    val genderOptions = listOf("Male", "Female")

    val viewModel: DoctorViewModel = viewModel()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher: ActivityResultLauncher<String> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Handle the selected image URI
            selectedImageUri = it
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Instead of image field I want i profile photo circle where I can click on it so
        // I can choose a photo from my gallery
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(color = Color.LightGray, shape = CircleShape)
                .clickable {
                    // Open the image picker when the profile photo circle is clicked
                    imagePickerLauncher.launch("image/*")
                }
        ) {
            // Display the selected image or a placeholder if none is chosen
            val painter: Painter = if (selectedImageUri != null) {
                rememberImagePainter(selectedImageUri)
            } else {
                // Placeholder or default image if no image is selected
                rememberImagePainter(R.drawable.oog)
            }

            Image(
                painter = painter,
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize()
                    .size(16.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = doctorName,
            onValueChange = { doctorName = it },
            label = { Text("Doctor's Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = doctorSpecialization,
            onValueChange = { doctorSpecialization = it },
            label = { Text("Specialization") },
            modifier = Modifier.fillMaxWidth()
        )


        TextField(
            value = doctorInfoOver,
            onValueChange = { doctorInfoOver = it },
            label = { Text("Infoe") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = doctorInfoOpleiding,
            onValueChange = { doctorInfoOpleiding = it },
            label = { Text("opleiding") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = doctorInfoPublicaties,
            onValueChange = { doctorInfoPublicaties = it },
            label = { Text("Publicatie") },
            modifier = Modifier.fillMaxWidth()
        )

        // Add more fields for additional doctor details

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val imageUriString: String? = selectedImageUri?.path
                val newDoctor = Doctor(
                    id = null,
                    name = doctorName,
                    specialization = doctorSpecialization,
                    gender = doctorGender,
                    infoOver = doctorInfoOver,
                    image = imageUriString,
                    infoOpleiding = doctorInfoOpleiding,
                    infoPublicaties = doctorInfoPublicaties
                )
                Log.d("foto check", "de string : ${selectedImageUri}")
                Log.d("foto check", "de string : ${imageUriString}")
                // Pass the newDoctor to a ViewModel or a function to add it to the list of doctors
                // viewModel.addDoctor(newDoctor)
               // viewModel.addDoctor(newDoctor)

                viewModel.addDoctor(newDoctor)


            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Add Doctor")
        }
    }
}