package com.example.templateapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.templateapplication.model.Appointment

@Entity(tableName = "appointments")
data class dbAppointment (
    @PrimaryKey
    val timeSlotId: Int,
    val reason: String,
    val note: String?,
    val patient: dbPatient,
)

fun dbAppointment.asDomainAppointment(): Appointment {
    return Appointment(timeSlotId = this.timeSlotId,
        reason = this.reason,
        note = this.note,
        patient = this.patient.asDomainPatient())
}

fun Appointment.asDbAppointment(): dbAppointment {
    return dbAppointment(timeSlotId = this.timeSlotId,
        reason = this.reason,
        note = this.note,
        patient = this.patient.asDbPatient())
}

fun List<dbAppointment>.asDomainAppointments(): List<Appointment> {
    var appointmentList = this.map {
        Appointment(it.timeSlotId, it.reason, it.note, it.patient.asDomainPatient())
    }
    return appointmentList
}