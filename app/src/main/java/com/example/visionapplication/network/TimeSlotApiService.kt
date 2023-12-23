package com.example.visionapplication.network

import com.example.visionapplication.model.TimeSlot
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Time slot api service
 *
 * @constructor Create empty Time slot api service
 */
interface TimeSlotApiService {
    /**
     * Get time slots
     *
     * @param id
     * @param headerValue
     * @return
     */
    @GET("timeslot/{doctorId}")
    suspend fun getTimeSlots(
        @Path("doctorId") id: Int,
        @Header("Authorization") headerValue: String
    ): List<TimeSlot>

    /**
     * Update time slot
     *
     * @param headerValue
     * @param timeSlot
     * @param id
     * @return
     */
    @PUT("timeslot/{docId}")
    suspend fun updateTimeSlot(
        @Header("Authorization") headerValue: String,
        @Body timeSlot: TimeSlot,
        @Path("docId") id: Int
    ) : TimeSlot

    /**
     * Create time slot
     *
     * @param headerValue
     * @param timeSlot
     * @return
     */
    @POST("timeslot")
    suspend fun createTimeSlot(
        @Header("Authorization") headerValue: String,
        @Body timeSlot: TimeSlot
    ) : TimeSlot

    /**
     * Delete time slot
     *
     * @param headerValue
     * @param timeSlot
     * @param id
     * @return
     */
    @HTTP(method = "DELETE", path = "timeslot/{doctorId}", hasBody = true)
    suspend fun deleteTimeSlot(
        @Header("Authorization") headerValue: String,
        @Body timeSlot: TimeSlot,
        @Path("doctorId") id: Int
    ) : Response<Unit>
}
