package com.example.visionapplication.network

import com.example.visionapplication.model.Appointment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

/**
 * Appointment api service
 *
 * @constructor Create empty Appointment api service
 */
interface AppointmentApiService {
    /**
     * Get appointments
     *
     * @param headerValue
     * @return
     */
    @GET("appointment")
    suspend fun getAppointments(
        @Header("Authorization") headerValue: String
    ): List<Appointment>

    /**
     * Update appointment
     *
     * @param headerValue
     * @param appointment
     * @return
     */
    @PUT("appointment")
    suspend fun updateAppointment(
        @Header("Authorization") headerValue: String,
        @Body appointment: Appointment
    ) : Appointment

    /**
     * Create appointment
     *
     * @param headerValue
     * @param appointment
     * @return
     */
    @POST("appointment")
    suspend fun createAppointment(
        @Header("Authorization") headerValue: String,
        @Body appointment: Appointment
    ) : Appointment

    /**
     * Delete appointment
     *
     * @param headerValue
     * @param appointment
     * @return
     */
    @HTTP(method = "DELETE", path = "appointment", hasBody = true)
    suspend fun deleteAppointment(
        @Header("Authorization") headerValue: String,
        @Body appointment: Appointment
    ) : Response<Unit>

}