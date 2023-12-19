package com.example.templateapplication.network

import com.example.templateapplication.model.TimeSlot
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL =
    "http://192.168.0.123:5001/api/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

interface TimeSlotApiService {
    @GET("timeslot/{id}")
    suspend fun getTimeSlots(@Path("id") id: Int): List<TimeSlot>
}

object TimeSlotApi {
    val retrofitService: TimeSlotApiService by lazy {
        retrofit.create(TimeSlotApiService::class.java)
    }
}
