package com.example.visionapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Note
 *
 * @property id
 * @property title
 * @property content
 * @constructor Create empty Note
 */
@Serializable
data class Note (
    @SerialName(value = "id")
    val id: Int?= null,
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "content")
    val content: String,
)