package com.example.templateapplication.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Doctor(
    val id: Int,
    @field:Json(name = "name") val name: String,
    val specialization: String,
    val gender: Int,
    val biograph: String?,
    val isAvailable: Boolean,
    @field:Json(name = "imageLink") val imageLink: String,
    val auth0Id: String
)