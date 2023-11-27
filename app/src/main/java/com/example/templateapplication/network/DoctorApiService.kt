package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "http://192.168.100.101:5046/api/"

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
interface DoctorApiService {
    @GET("Doctor/getDoctors")
    suspend fun getDoctors(): List<Doctor>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object DoctorApi {
    val retrofitService: DoctorApiService by lazy {
        retrofit.create(DoctorApiService::class.java)
    }
}



/*

interface ApiServiceDoctor {
    @GET("Doctor/getDoctors")
    suspend fun getDoctors(): List<ApiDoctor>
}



class DoctorApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.101:5046/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiServiceDoctor::class.java)

    suspend fun getDoctors(): List<ApiDoctor> {
        try {
            return apiService.getDoctors()
        } catch (e: Exception) {
            throw Exception("Failed to fetch doctors", e)
        }
    }
}

 */