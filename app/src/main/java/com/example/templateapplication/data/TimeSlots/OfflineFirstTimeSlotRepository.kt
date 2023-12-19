package com.example.templateapplication.data.TimeSlots

import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.network.TimeSlotApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OfflineFirstTimeSlotRepository(private val timeSlotDao: TimeSlotDao, private val timeSlotApi: TimeSlotApiService) : TimeSlotRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            updateTimeSlotsInBackground()
        }
    }

    private suspend fun updateTimeSlotsInBackground() {
        while (true) {
            refreshTimeSlots()
            delay(300000)
        }
    }

    override fun getTimeSlotsStream(doctorId: Int): Flow<List<TimeSlot>> {
        return timeSlotDao.getAllTimeSlots(doctorId).map { timeSlots -> timeSlots.map { it.asDomainTimeSlot() } }
            .onEach {
                if (it.isEmpty()) {
                    refreshTimeSlots()
                }
            }
    }

    override suspend fun insertTimeSlot(timeSlot: TimeSlot) {
        timeSlotDao.insert(timeSlot.asDbTimeSlot())
        timeSlotApi.createTimeSlot(timeSlot)
    }

    override suspend fun updateTimeSlot(timeSlot: TimeSlot) {
        timeSlotDao.update(timeSlot.asDbTimeSlot())
        timeSlotApi.updateTimeSlot(timeSlot)
    }

    override suspend fun deleteTimeSlot(timeSlot: TimeSlot) {
        timeSlotDao.delete(timeSlot.asDbTimeSlot())
        timeSlotApi.deleteTimeSlot(timeSlot)
    }

    override suspend fun refreshTimeSlots() {
        // TODO get the id's from doctors and refresh for each doctor
        timeSlotApi.getTimeSlots(3)
            .also { externalTimeSlots -> timeSlotDao.deleteAndInsert(timeSlots = externalTimeSlots.map(TimeSlot::asDbTimeSlot)) }
    }
}