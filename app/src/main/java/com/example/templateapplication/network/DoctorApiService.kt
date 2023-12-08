package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "http://192.168.100.101:5001/api/"


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

interface DoctorApiService {
    @GET("doctor")
    suspend fun getDoctors(): List<Doctor>
}

object DoctorApi {
    val retrofitService: DoctorApiService by lazy {
        retrofit.create(DoctorApiService::class.java)
    }
}
