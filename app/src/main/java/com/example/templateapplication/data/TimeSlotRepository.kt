package com.example.templateapplication.data

import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.network.TimeSlotApiService

interface TimeSlotRepository {
    suspend fun getTimeSlots(id: Int): List<TimeSlot>
}

class NetworkTimeSlotRepository(
    //private val timeSlotDao: TimeSlotDao,
    private val timeSlotApiService: TimeSlotApiService
): TimeSlotRepository {
    override suspend fun getTimeSlots(id: Int): List<TimeSlot> {
        return timeSlotApiService.getTimeSlots(id)
    }
}