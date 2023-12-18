package com.example.templateapplication.model

import java.sql.Timestamp

data class PatientDTO(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: Timestamp,
    val gender: Int,
    val bloodType: Int,
)
