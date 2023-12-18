package com.example.templateapplication.network

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.Patient
import retrofit2.http.Body
import retrofit2.http.GET

interface AppointmentApiService {
    @GET("appointment")
    suspend fun getAppointments(@Body body: Patient): List<Appointment>
}