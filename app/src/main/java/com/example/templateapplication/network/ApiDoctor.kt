package com.example.templateapplication.network

import com.example.templateapplication.model.Doctor
import kotlinx.serialization.Serializable

@Serializable
data class ApiDoctor(
    val id : Int,
    val name : String,
    val gender : String,
    val specialization : String,
    val infoOver: String,
    val infoOpleiding: String,
    val infoPublicaties: String,
    val image: String,
)

fun List<ApiDoctor>.asDomainObjects(): List<Doctor> {
    val domainList = this.map {
        Doctor(it.id, it.name, it.gender, it.specialization, it.infoOver, it.infoOpleiding, it.infoPublicaties, it.image)
    }
    return domainList
}