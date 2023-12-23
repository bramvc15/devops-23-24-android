package com.example.visionapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Doctor
 *
 * @property id
 * @property name
 * @property specialization
 * @property gender
 * @property biograph
 * @property isAvailable
 * @property imageLink
 * @property auth0Id
 * @constructor Create empty Doctor
 */
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