package com.example.visionapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Appointment
 *
 * @property timeSlotId
 * @property reason
 * @property note
 * @property patient
 * @constructor Create empty Appointment
 */
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