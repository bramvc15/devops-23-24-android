package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
import retrofit2.http.GET

interface DoctorApiService {
    @GET("doctor")
    suspend fun getDoctors(): List<Doctor>
}
