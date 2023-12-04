package com.example.templateapplication.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Serializable
data class PatientDTO(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    @Serializable(with = LocalDateTimeSerializer::class) val dateOfBirth: LocalDateTime,
    val gender: Int,
    val bloodType: Int,
)
