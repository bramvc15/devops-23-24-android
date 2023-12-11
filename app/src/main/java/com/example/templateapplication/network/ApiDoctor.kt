package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class ApiDoctor(
    val id: Int,
    val name: String,
    val specialization: String,
    val gender: Int,
    val biograph: String,
    val isAvailable: Boolean,
    val imageLink: String,
)

fun List<ApiDoctor>.asDomainObjects(): List<Doctor> {
    val domainList = this.map {
        Doctor(it.id, it.name, it.specialization, it.gender, it.biograph, it.isAvailable, it.imageLink)
    }
    return domainList
}