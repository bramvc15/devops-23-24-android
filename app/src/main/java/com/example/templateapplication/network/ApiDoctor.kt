package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
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
        val auth0Id : String
)

fun List<ApiDoctor>.asDomainObjects(): List<Doctor> {
    val domainList = this.map {
        Doctor(it.id, it.name, it.specialization, it.gender, it.biograph, it.isAvailable, it.imageLink, it.auth0Id)
    }
    return domainList
}