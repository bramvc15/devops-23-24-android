package com.example.templateapplication.data.TimeSlots

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.templateapplication.data.Appointments.asDbAppointment
import com.example.templateapplication.data.Appointments.asDomainAppointment
import com.example.templateapplication.data.Appointments.dbAppointment
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.TimeSlot

@Entity(tableName = "timeslots")
data class dbTimeSlot (
    @PrimaryKey
    val timeslotId: Int,
    val doctorId: Int,
    val appointmentType: Int,
    val dateTime: String,
    val duration: Int,
    @Embedded val appointment: dbAppointment?,
)

fun dbTimeSlot.asDomainTimeSlot(): TimeSlot {

    // check if appointment is not null
    var a: Appointment? = null
    if (this.appointment != null) {
        a = this.appointment.asDomainAppointment()
    }
    return TimeSlot(id = this.timeslotId,
        doctorId = this.doctorId,
        appointmentType = this.appointmentType,
        dateTime = this.dateTime,
        duration = this.duration,
        appointment = a)
}

fun TimeSlot.asDbTimeSlot(): dbTimeSlot? {

    // check if appointment is not null
    var a: dbAppointment? = null
    if (this.appointment != null) {
        a = this.appointment.asDbAppointment()
    }

    return this.doctorId?.let {
        dbTimeSlot(timeslotId = this.id,
        doctorId = it,
        appointmentType = this.appointmentType,
        dateTime = this.dateTime,
        duration = this.duration,
        appointment = a)
    }
}

fun List<dbTimeSlot>.asDomainTimeSlots(): List<TimeSlot> {
    var timeSlotList = this.map {

        // check if appointment is not null
        var a: Appointment? = null
        if (it.appointment != null) {
            a = it.appointment.asDomainAppointment()
        }

        TimeSlot(it.timeslotId, it.doctorId, it.appointmentType, it.dateTime, it.duration, a)
    }
    return timeSlotList
}