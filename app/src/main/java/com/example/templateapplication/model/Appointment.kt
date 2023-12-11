package com.example.templateapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val id: Int,
    val reason: String,
    val note: String,
    val patientDTO: PatientDTO,
)