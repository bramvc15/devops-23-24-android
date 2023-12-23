package com.example.visionapplication.network

import com.example.visionapplication.model.Doctor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

/**
 * Doctor api service
 *
 * @constructor Create empty Doctor api service
 */
interface DoctorApiService {
    /**
     * Get doctors
     *
     * @param headerValue
     * @return
     */
    @GET("Doctor")
    suspend fun getDoctors(
        @Header("Authorization") headerValue: String
    ): List<Doctor>

    /**
     * Update doctor
     *
     * @param headerValue
     * @param doctor
     * @return
     */
    @PUT("doctor")
    suspend fun updateDoctor(
        @Header("Authorization") headerValue: String,
       @Body doctor: Doctor
    ) : Doctor

    /**
     * Delete doctor
     *
     * @param headerValue
     * @param doctor
     * @return
     */
    @DELETE("doctor")
    suspend fun deleteDoctor(
        @Header("Authorization") headerValue: String,
        @Body doctor: Doctor
    ) : Response<Unit>

    /**
     * Create doctor
     *
     * @param headerValue
     * @param doctor
     * @return
     */
    @POST("doctor")
    suspend fun createDoctor(
        @Header("Authorization") headerValue: String,
        @Body doctor: Doctor
    ) : Doctor
}
