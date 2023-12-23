package com.example.visionapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Doctor(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "specialization")
    val specialization: String,
    @SerialName(value = "gender")
    val gender: Int,
    @SerialName(value = "biograph")
    val biograph: String?,
    @SerialName(value = "isAvailable")
    val isAvailable: Boolean,
    @SerialName(value = "imageLink")
    val imageLink: String,
    @SerialName(value = "auth0Id")
    val auth0Id: String,
)