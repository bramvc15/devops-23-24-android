package com.example.templateapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.templateapplication.model.Patient
import java.sql.Timestamp

@Entity(tableName = "patients")
data class dbPatient (
    @PrimaryKey()
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val gender: Int,
    val bloodType: Int,
)

fun dbPatient.asDomainPatient(): Patient {
    return Patient(id = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        bloodType = this.bloodType)
}

fun Patient.asDbPatient(): dbPatient {
    return dbPatient(id = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        bloodType = this.bloodType)
}

fun List<dbPatient>.asDomainPatients(): List<Patient> {
    var patientList = this.map {
        Patient(it.id, it.name, it.email, it.phoneNumber, it.dateOfBirth, it.gender, it.bloodType)
    }
    return patientList
}