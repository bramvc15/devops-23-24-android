package com.example.templateapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    @SerialName(value = "id")
    val timeSlotId: Int,
    @SerialName(value = "reason")
    val reason: String,
    @SerialName(value = "note")
    val note: String?,
    @SerialName(value = "patientDTO")
    val patient: Patient,
)