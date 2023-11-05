package com.example.templateapplication.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapplication.data.fetchDataFromDatabase
import kotlinx.coroutines.launch

class DoctorsViewModel : ViewModel() {
   // private val dbConnection = DbConnection() // Replace with your actual DbConnection class

    var doctors: List<Doctor> by mutableStateOf(emptyList())

    fun fetchDoctorsData() {
        viewModelScope.launch {
            doctors = fetchDataFromDatabase()
        }
    }
}
