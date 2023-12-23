package com.example.templateapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note (
    @SerialName(value = "id")
    val id: Int?= null,
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "content")
    val content: String,
)