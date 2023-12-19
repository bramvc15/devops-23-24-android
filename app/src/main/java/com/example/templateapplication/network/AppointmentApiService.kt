package com.example.templateapplication.network

import com.example.templateapplication.model.Appointment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AppointmentApiService {
    @GET("appointment")
    suspend fun getAppointments(): List<Appointment>

    @PUT("appointment")
    suspend fun updateAppointment(
        @Body appointment: Appointment
    ) : Appointment

    @POST("appointment")
    suspend fun createAppointment(
        @Body appointment: Appointment
    ) : Appointment

    @DELETE("appointment")
    suspend fun deleteAppointment(
        @Body appointment: Appointment
    ) : Response<Unit>

}