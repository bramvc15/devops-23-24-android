package com.example.templateapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface ApiServiceDoctor {
    @GET("doctors")
    suspend fun getDoctors(): List<ApiDoctor>
}



class DoctorApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.101:5046/api/Doctor/getDoctors")
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