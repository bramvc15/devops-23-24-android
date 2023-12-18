package com.example.templateapplication.data

import androidx.room.Entity
import com.example.templateapplication.model.TimeSlot
import java.sql.Timestamp

@Entity(tableName = "timeslots")
data class dbTimeSlot (
    val id: Int,
    val duration: Int,
    val appointmentType: Int,
    val dateTime: Timestamp,
    val doctorId: Int,
)

fun dbTimeSlot.asDomainTimeSlot(): TimeSlot {
    return TimeSlot(id = this.id,
        duration = this.duration,
        appointmentType = this.appointmentType,
        dateTime = this.dateTime,
        doctorId = this.doctorId,
        appointmentDTO = null)
}

fun TimeSlot.asDbTimeSlot(): dbTimeSlot {
    return dbTimeSlot(id = this.id,
        duration = this.duration,
        appointmentType = this.appointmentType,
        dateTime = this.dateTime,
        doctorId = this.doctorId)
}

fun List<dbTimeSlot>.asDomainTimeSlots(): List<TimeSlot> {
    var timeSlotList = this.map {
        TimeSlot(it.id, it.duration, it.appointmentType, it.dateTime, null, it.doctorId)
    }
    return timeSlotList
}