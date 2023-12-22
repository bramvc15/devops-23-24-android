package com.example.templateapplication.network

import com.example.templateapplication.model.TimeSlot
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimeSlotApiService {
    @GET("timeslot/{doctorId}")
    suspend fun getTimeSlots(
        @Path("doctorId") id: Int,
        @Header("Authorization") headerValue: String
    ): List<TimeSlot>

    @PUT("timeslot/{docId}")
    suspend fun updateTimeSlot(
        @Header("Authorization") headerValue: String,
        @Body timeSlot: TimeSlot,
        @Path("docId") id: Int
    ) : TimeSlot

    @POST("timeslot")
    suspend fun createTimeSlot(
        @Header("Authorization") headerValue: String,
        @Body timeSlot: TimeSlot
    ) : TimeSlot

    @HTTP(method = "DELETE", path = "timeslot/{doctorId}", hasBody = true)
    suspend fun deleteTimeSlot(
        @Header("Authorization") headerValue: String,
        @Body timeSlot: TimeSlot,
        @Path("doctorId") id: Int
    ) : Response<Unit>
}
