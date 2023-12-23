package com.example.visionapplication.model

import com.example.visionapplication.data.GlobalDoctor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeSlot(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "doctorId")
    val doctorId: Int? = GlobalDoctor.doctor?.id,
    @SerialName(value = "appointmentType")
    val appointmentType: Int,
    @SerialName(value = "dateTime")
    val dateTime: String,
    @SerialName(value = "duration")
    val duration: Int,
    @SerialName(value = "appointmentDTO")
    val appointment: Appointment?,
)