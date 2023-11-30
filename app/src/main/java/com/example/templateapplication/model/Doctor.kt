package com.example.templateapplication.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Doctor(
    val id: Int?,
    @field:Json(name = "name") val name: String,
    val gender: String,
    val specialization: String,
    val infoOver: String,
    val infoOpleiding: String,
    val infoPublicaties: String,
    @field:Json(name = "image") val image: String,
)