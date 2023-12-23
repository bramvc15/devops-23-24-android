package com.example.visionapplication.data.Appointments

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.visionapplication.data.Patients.asDbPatient
import com.example.visionapplication.data.Patients.asDomainPatient
import com.example.visionapplication.data.Patients.dbPatient
import com.example.visionapplication.model.Appointment

/**
 * Db appointment
 *
 * @property timeSlotId
 * @property reason
 * @property note
 * @property patient
 * @constructor Create empty Db appointment
 */
@Entity(tableName = "appointments")
data class dbAppointment (
    @PrimaryKey
    @ColumnInfo(name = "appointment_id")
    val timeSlotId: Int,
    val reason: String,
    val note: String?,
    @Embedded val patient: dbPatient,
)

/**
 * As domain appointment
 *
 * @return
 */
fun dbAppointment.asDomainAppointment(): Appointment {
    return Appointment(timeSlotId = this.timeSlotId,
        reason = this.reason,
        note = this.note,
        patient = this.patient.asDomainPatient())
}

/**
 * As db appointment
 *
 * @return
 */
fun Appointment.asDbAppointment(): dbAppointment {
    return dbAppointment(timeSlotId = this.timeSlotId,
        reason = this.reason,
        note = this.note,
        patient = this.patient.asDbPatient())
}

/**
 * As domain appointments
 *
 * @return
 */
fun List<dbAppointment>.asDomainAppointments(): List<Appointment> {
    var appointmentList = this.map {
        Appointment(it.timeSlotId, it.reason, it.note, it.patient.asDomainPatient())
    }
    return appointmentList
}