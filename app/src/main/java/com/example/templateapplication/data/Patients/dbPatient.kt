package com.example.templateapplication.data.Patients

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.templateapplication.model.Patient

@Entity(tableName = "patients")
data class dbPatient (
    @PrimaryKey
    @ColumnInfo(name = "patient_id")
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val gender: Int,
    val bloodType: Int,
)

fun dbPatient.asDomainPatient(): Patient {
    return Patient(id = this.patientId,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        bloodType = this.bloodType)
}

fun Patient.asDbPatient(): dbPatient {
    return dbPatient(patientId = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        bloodType = this.bloodType)
}

fun List<dbPatient>.asDomainPatients(): List<Patient> {
    var patientList = this.map {
        Patient(it.patientId, it.name, it.email, it.phoneNumber, it.dateOfBirth, it.gender, it.bloodType)
    }
    return patientList
}