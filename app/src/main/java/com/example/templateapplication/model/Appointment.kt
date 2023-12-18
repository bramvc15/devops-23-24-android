package com.example.templateapplication.model

data class Appointment(
    val timeSlotId: Int,
    val reason: String,
    val note: String?,
    val patient: Patient?,
    val patientId: Int,
)