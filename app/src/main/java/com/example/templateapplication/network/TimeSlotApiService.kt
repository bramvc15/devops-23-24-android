package com.example.templateapplication.network

import com.example.templateapplication.model.TimeSlot
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimeSlotApiService {
    @GET("timeslot/{id}")
    suspend fun getTimeSlots(@Path("id") id: Int): List<TimeSlot>

    @PUT("timeslot")
    suspend fun updateTimeSlot(
        @Body timeSlot: TimeSlot
    ) : TimeSlot

    @POST("timeslot")
    suspend fun createTimeSlot(
        @Body timeSlot: TimeSlot
    ) : TimeSlot

    @DELETE("timeslot")
    suspend fun deleteTimeSlot(
        @Body timeSlot: TimeSlot
    ) : Response<Unit>
}
