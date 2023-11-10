package com.example.templateapplication.models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.templateapplication.data.DoctorService


class DoctorViewModel : ViewModel() {
    val doctors = mutableStateListOf<Doctor>()
    val selectedDoctor = mutableStateOf<Doctor?>(null)

    private val doctorService = DoctorService()
    fun fetchDoctors() {

        viewModelScope.launch {
            try {

                val doctorList = doctorService.getDoctors()
                doctors.clear()
                doctors.addAll(doctorList)

            } catch (e: Exception) {
                // Handle the exception, e.g., show an error message
                e.printStackTrace()
            }
        }
    }

    fun getDoctorById(id : Int){
        viewModelScope.launch {
            try {

                val doctor = doctorService.getDoctorById(id)


                if (doctor != null) {
                    selectedDoctor.value = doctor
                }

            } catch (e: Exception) {
                // Handle the exception, e.g., show an error message
                e.printStackTrace()
            }
        }
    }
}

