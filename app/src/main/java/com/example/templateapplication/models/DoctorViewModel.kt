package com.example.templateapplication.models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.templateapplication.data.DoctorService


class DoctorViewModel : ViewModel() {
    val doctors = mutableStateListOf<Doctor>()

    private val doctorService = DoctorService()
    fun fetchDoctors() {

        viewModelScope.launch {
            try {
                val doctorList = doctorService.getDoctors()
                doctors.addAll(doctorList)
            } catch (e: Exception) {
                Log.d("jdfkgljsdklgjsdkl", "gjskldfjgààààààààààklsdf")
                // Handle the exception, e.g., show an error message
                e.printStackTrace()
            }
        }
    }
}

