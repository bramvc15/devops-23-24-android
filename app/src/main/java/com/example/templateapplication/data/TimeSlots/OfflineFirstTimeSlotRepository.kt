package com.example.templateapplication.data.TimeSlots

import com.example.templateapplication.data.GlobalDoctor
import com.example.templateapplication.model.Doctor
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
        timeSlot.asDbTimeSlot()?.let { timeSlotDao.insert(it) }
        timeSlotApi.createTimeSlot(GlobalDoctor.authedDoctor!!.bearerToken, timeSlot)
    }

    override suspend fun updateTimeSlot(timeSlot: TimeSlot) {
        timeSlot.asDbTimeSlot()?.let { timeSlotDao.update(it) }
        timeSlotApi.updateTimeSlot(GlobalDoctor.authedDoctor!!.bearerToken, timeSlot, timeSlot.doctorId!!)
    }

    override suspend fun deleteTimeSlot(timeSlot: TimeSlot) {
        timeSlot.asDbTimeSlot()?.let { timeSlotDao.delete(it) }
        timeSlotApi.deleteTimeSlot(GlobalDoctor.authedDoctor!!.bearerToken, timeSlot, timeSlot.doctorId!!)
    }

    override suspend fun refreshTimeSlots() {
        // TODO get the id's from doctors and refresh for each doctor
        getSelectedDoctor()?.let {
            timeSlotApi.getTimeSlots(it.id, GlobalDoctor.authedDoctor!!.bearerToken)
                .also { externalTimeSlots -> timeSlotDao.deleteAndInsert(timeSlots = externalTimeSlots.map(TimeSlot::asDbTimeSlot)) }
        }
    }

    fun getSelectedDoctor(): Doctor? {
        return GlobalDoctor.doctor
    }

}