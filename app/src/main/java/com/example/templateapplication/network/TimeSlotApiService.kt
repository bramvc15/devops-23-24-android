package com.example.templateapplication.network

import com.example.templateapplication.model.TimeSlot
import retrofit2.http.GET
import retrofit2.http.Path

interface TimeSlotApiService {
    @GET("timeslot/{id}")
    suspend fun getTimeSlots(@Path("id") id: Int): List<TimeSlot>
}
