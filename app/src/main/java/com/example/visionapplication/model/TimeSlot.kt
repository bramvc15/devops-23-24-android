package com.example.visionapplication.model

import com.example.visionapplication.data.GlobalDoctor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Time slot
 *
 * @property id
 * @property doctorId
 * @property appointmentType
 * @property dateTime
 * @property duration
 * @property appointment
 * @constructor Create empty Time slot
 */
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