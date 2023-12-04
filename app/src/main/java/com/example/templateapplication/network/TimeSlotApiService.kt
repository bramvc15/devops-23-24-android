package com.example.templateapplication.network

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.model.PatientDTO
import com.example.templateapplication.model.TimeSlot
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "http://192.168.0.227:5046/api/"

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface TimeSlotApiService {
    @GET("timeslot/{id}")
    suspend fun getTimeSlots(@Path("id") id: Int): List<TimeSlot>
}

object TimeSlotApi {
    val retrofitService: TimeSlotApiService by lazy {
        retrofit.create(TimeSlotApiService::class.java)
    }
}
