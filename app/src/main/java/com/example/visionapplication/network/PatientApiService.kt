package com.example.visionapplication.network

import com.example.visionapplication.model.Patient
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Patient api service
 *
 * @constructor Create empty Patient api service
 */
interface PatientApiService {
    /**
     * Get patients
     *
     * @param headerValue
     * @return
     */
    @GET("patient")
    suspend fun getPatients(
        @Header("Authorization") headerValue: String
    ): List<Patient>
}
