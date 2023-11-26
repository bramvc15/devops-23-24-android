package com.example.templateapplication.data

import com.example.templateapplication.network.DoctorApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val doctorsRepository: DoctorsRepository
}


class DefaultAppContainer(): AppContainer{

    private val baseUrl = "http://192.168.100.101:5046"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : DoctorApiService by lazy {
        retrofit.create(DoctorApiService::class.java)
    }


    override val doctorsRepository: DoctorsRepository by lazy {
        ApiDoctorsRepository(retrofitService)
    }

}