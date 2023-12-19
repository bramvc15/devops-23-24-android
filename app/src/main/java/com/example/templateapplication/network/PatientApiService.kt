package com.example.templateapplication.network

import com.example.templateapplication.model.Patient
import retrofit2.http.GET

interface PatientApiService {
    @GET("patient")
    suspend fun getPatients(): List<Patient>
}
