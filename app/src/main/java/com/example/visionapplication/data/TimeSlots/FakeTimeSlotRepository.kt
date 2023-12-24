package com.example.visionapplication.data.TimeSlots

import com.example.visionapplication.model.TimeSlot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTimeSlotRepository(private val timeSlotList: MutableList<TimeSlot>) : TimeSlotRepository {
    override fun getTimeSlotsStream(doctorId: Int): Flow<List<TimeSlot>> = flow {
        emit(timeSlotList)
    }

    override suspend fun insertTimeSlot(timeSlot: TimeSlot) {
    }

    override suspend fun updateTimeSlot(timeSlot: TimeSlot) {
    }

    override suspend fun deleteTimeSlot(timeSlot: TimeSlot) {
        timeSlotList.remove(timeSlot)
    }

    override suspend fun refreshTimeSlots() {
    }

}