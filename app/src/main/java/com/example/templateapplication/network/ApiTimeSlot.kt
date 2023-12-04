package com.example.templateapplication.network

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.model.PatientDTO
import com.example.templateapplication.model.TimeSlot
import com.squareup.moshi.Json
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.Date

@Serializable
data class ApiTimeSlot(
    val id: Int,
    val appointmentType: Int,
    @Contextual val dateTime: LocalDateTime,
    val duration: Int,
    val appointmentDTO: Appointment,
)

fun List<ApiTimeSlot>.asDomainObjects(): List<TimeSlot> {
    val domainList = this.map {
        TimeSlot(
            id = it.id,
            appointmentType = it.appointmentType,
            dateTime = it.dateTime,
            duration = it.duration,
            appointmentDTO = it.appointmentDTO,
        )
    }
    return domainList
}