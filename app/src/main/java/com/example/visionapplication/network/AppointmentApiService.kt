package com.example.visionapplication.network

import com.example.visionapplication.model.Appointment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AppointmentApiService {
    @GET("appointment")
    suspend fun getAppointments(
        @Header("Authorization") headerValue: String
    ): List<Appointment>

    @PUT("appointment")
    suspend fun updateAppointment(
        @Header("Authorization") headerValue: String,
        @Body appointment: Appointment
    ) : Appointment

    @POST("appointment")
    suspend fun createAppointment(
        @Header("Authorization") headerValue: String,
        @Body appointment: Appointment
    ) : Appointment

    @HTTP(method = "DELETE", path = "appointment", hasBody = true)
    suspend fun deleteAppointment(
        @Header("Authorization") headerValue: String,
        @Body appointment: Appointment
    ) : Response<Unit>

}