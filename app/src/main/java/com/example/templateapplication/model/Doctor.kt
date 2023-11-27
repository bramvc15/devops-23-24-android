package com.example.templateapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Doctor(
    val id: Int?,
    val name: String,
    val gender: String,
    val specialization: String,
    val infoOver: String,
    val infoOpleiding: String,
    val infoPublicaties: String,
    val image: String,
)