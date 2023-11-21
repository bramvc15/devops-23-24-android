package com.example.templateapplication.screens

import android.net.Uri
import android.util.Base64
import android.util.Log
import android.content.ContentResolver
import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.templateapplication.Constants.context
import com.example.templateapplication.R
import com.example.templateapplication.models.Doctor
import com.example.templateapplication.models.DoctorViewModel
import com.example.templateapplication.navigation.Screens
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream



@Composable
fun AddDoctorScreen(navController : NavController) {
    var doctorName by remember { mutableStateOf("") }
    var doctorSpecialization by remember { mutableStateOf("") }
    var doctorGender by remember { mutableStateOf("") }
    var doctorInfoOver by remember { mutableStateOf("") }
    var doctorInfoOpleiding by remember { mutableStateOf("") }
    var doctorInfoPublicaties by remember { mutableStateOf("")}

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val originalString = selectedImageUri
    val contentResolver = LocalContext.current.contentResolver

    val viewModel: DoctorViewModel = viewModel()

    val imagePickerLauncher: ActivityResultLauncher<String> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(color = Color.LightGray, shape = CircleShape)
                .clickable {
                    imagePickerLauncher.launch("image/*")
                }
        ) {
            val painter: Painter = if (selectedImageUri != null) {
                rememberImagePainter(selectedImageUri)
            } else {
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

        Spacer(modifier = Modifier.height(16.dp))

        val coroutineScope = rememberCoroutineScope()

        Button(
            onClick = {
                try {
                    val uriT642 = getBase64ForUri2(originalString, contentResolver)

                    val newDoctor = Doctor(
                        id = null,
                        name = doctorName,
                        specialization = doctorSpecialization,
                        gender = doctorGender,
                        infoOver = doctorInfoOver,
                        image = uriT642,
                        infoOpleiding = doctorInfoOpleiding,
                        infoPublicaties = doctorInfoPublicaties,
                        imageBase64 = null
                    )

                    viewModel.addDoctor(newDoctor)

                    // Use CoroutineScope to launch a coroutine
                    coroutineScope.launch {
                        // Show a toast message
                        Toast.makeText(
                            context,
                            "Doctor added successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate to DoctorScreen
                        navController.navigate(Screens.DoctorsScreen.name)
                    }
                } catch (e: Exception) {

                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Add Doctor")
        }
    }

}

fun getBase64ForUri2(uri: Uri? , contentResolver : ContentResolver): String {
    try {

        val bytes = uri?.let { contentResolver.openInputStream (it)?.readBytes () }
        return Base64.encodeToString (bytes, Base64.DEFAULT)
    } catch (error: IOException) {
        error.printStackTrace ()
        Toast.makeText(context, "Error while processing image", Toast.LENGTH_SHORT).show()
    }
    return ""
}


