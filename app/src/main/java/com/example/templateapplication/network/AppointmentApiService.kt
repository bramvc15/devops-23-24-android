package com.example.templateapplication.network

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.PatientDTO
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET

private const val BASE_URL =
    "http://192.168.0.123:5001/api/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

interface AppointmentApiService {
    @GET("appointment")
    suspend fun getAppointments(@Body body: PatientDTO): List<Appointment>
}

object AppointmentApi {
    val retrofitService: AppointmentApiService by lazy {
        retrofit.create(AppointmentApiService::class.java)
    }
}