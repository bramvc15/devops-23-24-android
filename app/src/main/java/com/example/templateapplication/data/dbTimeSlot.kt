package com.example.templateapplication.data

import androidx.room.Entity
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.TimeSlot
import java.sql.Timestamp

@Entity(tableName = "timeslots")
data class dbTimeSlot (
    val id: Int,
    val doctorId: Int,
    val appointmentType: Int,
    val dateTime: String,
    val duration: Int,
    val appointment: dbAppointment,
)

fun dbTimeSlot.asDomainTimeSlot(): TimeSlot {
    return TimeSlot(id = this.id,
        doctorId = this.doctorId,
        appointmentType = this.appointmentType,
        dateTime = this.dateTime,
        duration = this.duration,
        appointment = this.appointment.asDomainAppointment())
}

fun TimeSlot.asDbTimeSlot(): dbTimeSlot {
    return dbTimeSlot(id = this.id,
        doctorId = this.doctorId,
        appointmentType = this.appointmentType,
        dateTime = this.dateTime,
        duration = this.duration,
        appointment = this.appointment.asDbAppointment())
}

fun List<dbTimeSlot>.asDomainTimeSlots(): List<TimeSlot> {
    var timeSlotList = this.map {
        TimeSlot(it.id, it.doctorId, it.appointmentType, it.dateTime, it.duration, it.appointment.asDomainAppointment())
    }
    return timeSlotList
}