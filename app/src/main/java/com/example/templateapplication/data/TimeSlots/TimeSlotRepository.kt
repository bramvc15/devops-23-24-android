package com.example.templateapplication.data.TimeSlots

import com.example.templateapplication.model.TimeSlot
import kotlinx.coroutines.flow.Flow

interface TimeSlotRepository {
    fun getTimeSlotsStream(doctorId: Int): Flow<List<TimeSlot>>
    suspend fun insertTimeSlot(timeSlot: TimeSlot)
    suspend fun updateTimeSlot(timeSlot: TimeSlot)
    suspend fun deleteTimeSlot(timeSlot: TimeSlot)
    suspend fun refreshTimeSlots()
}