package com.example.visionapplication.data.TimeSlots

import com.example.visionapplication.model.TimeSlot
import kotlinx.coroutines.flow.Flow

/**
 * Time slot repository
 *
 * @constructor Create empty Time slot repository
 */
interface TimeSlotRepository {
    /**
     * Get time slots stream
     *
     * @param doctorId
     * @return
     */
    fun getTimeSlotsStream(doctorId: Int): Flow<List<TimeSlot>>

    /**
     * Insert time slot
     *
     * @param timeSlot
     */
    suspend fun insertTimeSlot(timeSlot: TimeSlot)

    /**
     * Update time slot
     *
     * @param timeSlot
     */
    suspend fun updateTimeSlot(timeSlot: TimeSlot)

    /**
     * Delete time slot
     *
     * @param timeSlot
     */
    suspend fun deleteTimeSlot(timeSlot: TimeSlot)

    /**
     * Refresh time slots
     *
     */
    suspend fun refreshTimeSlots()
}