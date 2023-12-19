package com.example.templateapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Patient(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "email")
    val email: String,
    @SerialName(value = "phoneNumber")
    val phoneNumber: String,
    @SerialName(value = "dateOfBirth")
    val dateOfBirth: String,
    @SerialName(value = "gender")
    val gender: Int,
    @SerialName(value = "bloodType")
    val bloodType: Int,
)
