package com.example.templateapplication.screens

import android.net.Uri
import android.util.Base64
import android.util.Log
import android.content.ContentResolver
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.templateapplication.Constants.context
import com.example.templateapplication.R
import com.example.templateapplication.models.Doctor
import com.example.templateapplication.models.DoctorViewModel
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream



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
            selectedImageUri = it
        }
    }

    Log.d("uri", "${selectedImageUri}")
    val originalString = selectedImageUri
    val contentResolver = LocalContext.current.contentResolver

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

        Button(
            onClick = {
                val imageUriString: String? = selectedImageUri?.path
                //val test = uriToHexadecimalString(contentResolver, selectedImageUri)
                val uriTBA = uriToByteArray(contentResolver , selectedImageUri)
                val uriT64 = getBase64ForUri(originalString, contentResolver)
                val uriT642 = getBase64ForUri2(originalString, contentResolver)
                val blob = uriToBlob(originalString, contentResolver)

                val uriBy = uriToBytes(context ,selectedImageUri)
                Log.d("test", "uri to ba : ${uriTBA}")
                Log.d("test2", "uri 64 : ${uriT64}")
                Log.d("test3", "uri 64 2  : ${uriT642}")
                Log.d("test4", "blob ${blob}")

                val base64String = uriT642
                val byteArray = base64ToByteArray(base64String)

                Log.d("test5", "(: ${byteArray}")
                Log.d("test6", "(: ${uriBy}")

                val newDoctor = Doctor(
                    id = null,
                    name = doctorName,
                    specialization = doctorSpecialization,
                    gender = doctorGender,
                    infoOver = doctorInfoOver,
                    image = uriT64,
                    infoOpleiding = doctorInfoOpleiding,
                    infoPublicaties = doctorInfoPublicaties,
                    imageBase64 = "f"
                )
                viewModel.addDoctor(newDoctor)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Add Doctor")
        }
    }
}

//1
fun uriToByteArray(contentResolver: ContentResolver, imageUri: Uri?): ByteArray? {
    if (imageUri == null) {
        return null // Handle null URI as needed
    }

    val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
    val byteArrayOutputStream = ByteArrayOutputStream()

    inputStream?.use { input ->
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }
    }

    return byteArrayOutputStream.toByteArray()
}

//2
fun getBase64ForUri(uri: Uri?, contentResolver: ContentResolver): ByteArray? {
    try {
        val bytes = uri?.let { contentResolver.openInputStream(it)?.readBytes() }
        return bytes
    } catch (error: IOException) {
        error.printStackTrace()
        return null // Returning null in case of an error
    }
}

//3
fun getBase64ForUri2(uri: Uri? , contentResolver : ContentResolver): String {
    try {

        val bytes = uri?.let { contentResolver.openInputStream (it)?.readBytes () }
        return Base64.encodeToString (bytes, Base64.DEFAULT)
    } catch (error: IOException) {
        error.printStackTrace ()
    }
    return "hellos"
}

//4
fun uriToBlob(uri: Uri?, contentResolver: ContentResolver): ByteArray? {
    try {
        uri?.let { actualUri ->
            contentResolver.openInputStream(actualUri)?.use { inputStream ->
                return inputStream.readBytes()
            }
        }
    } catch (error: IOException) {
        error.printStackTrace()
    }
    return null
}

fun base64ToByteArray(base64String: String): ByteArray {
    return Base64.decode(base64String, Base64.URL_SAFE)
}

fun uriToBytes(context: Context, imageUri: Uri?): ByteArray {
    val contentResolver: ContentResolver = context.contentResolver
    val outputStream = ByteArrayOutputStream()

    try {
        val inputStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
        inputStream?.use { input ->
            val buffer = ByteArray(4 * 1024) // Adjust buffer size as necessary
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return outputStream.toByteArray()
}