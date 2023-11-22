package com.example.templateapplication.network

import com.example.templateapplication.models.Doctor
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
    val image: String?,
    val imageBase64: String?
)


//extension function for an ApiDoctor List to convert is to a Domain Doctor List
fun List<ApiDoctor>.asDomainObjects(): List<Doctor> {
    var domainList = this.map {
        Doctor(it.id, it.name, it.gender, it.specialization, it.infoOver, it.infoOpleiding, it.infoPublicaties, it.image, it.imageBase64)
    }
    return domainList
}