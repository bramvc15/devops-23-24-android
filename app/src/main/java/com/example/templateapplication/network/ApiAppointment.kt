package com.example.templateapplication.network

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.model.PatientDTO
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class ApiAppointment(
    val id: Int,
    val reason: String,
    val note: String,
    val patientDTO: PatientDTO,
)

fun List<ApiAppointment>.asDomainObjects(): List<Appointment> {
    val domainList = this.map {
        Appointment(
            id = it.id,
            reason = it.reason,
            note = it.note,
            patientDTO = it.patientDTO
        )
    }
    return domainList
}