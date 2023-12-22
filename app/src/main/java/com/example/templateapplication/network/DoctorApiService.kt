package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface DoctorApiService {
    @GET("Doctor")
    suspend fun getDoctors(
        @Header("Authorization") headerValue: String
    ): List<Doctor>

    @PUT("doctor")
    suspend fun updateDoctor(
        @Header("Authorization") headerValue: String,
       @Body doctor: Doctor
    ) : Doctor

    @DELETE("doctor")
    suspend fun deleteDoctor(
        @Header("Authorization") headerValue: String,
        @Body doctor: Doctor
    ) : Response<Unit>

    @POST("doctor")
    suspend fun createDoctor(
        @Header("Authorization") headerValue: String,
        @Body doctor: Doctor
    ) : Doctor
}
