package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface DoctorApiService {
    @GET("doctor")
    suspend fun getDoctors(): List<Doctor>

    @PUT("doctor")
    suspend fun updateDoctor(
       @Body doctor: Doctor
    ) : Doctor

    @DELETE("doctor")
    suspend fun deleteDoctor(
        @Body doctor: Doctor
    ) : Response<Unit>

    @POST("doctor")
    suspend fun createDoctor(
        @Body doctor: Doctor
    ) : Doctor
}
