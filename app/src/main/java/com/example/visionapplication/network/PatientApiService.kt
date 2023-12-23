package com.example.visionapplication.network

import com.example.visionapplication.model.Patient
import retrofit2.http.GET
import retrofit2.http.Header

interface PatientApiService {
    @GET("patient")
    suspend fun getPatients(
        @Header("Authorization") headerValue: String
    ): List<Patient>
}
