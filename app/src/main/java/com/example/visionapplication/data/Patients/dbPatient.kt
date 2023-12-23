package com.example.visionapplication.data.Patients

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.visionapplication.model.Patient

/**
 * Db patient
 *
 * @property id
 * @property name
 * @property email
 * @property phoneNumber
 * @property dateOfBirth
 * @property gender
 * @property bloodType
 * @constructor Create empty Db patient
 */
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

/**
 * As domain patient
 *
 * @return
 */
fun dbPatient.asDomainPatient(): Patient {
    return Patient(id = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        bloodType = this.bloodType)
}

/**
 * As db patient
 *
 * @return
 */
fun Patient.asDbPatient(): dbPatient {
    return dbPatient(id = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        bloodType = this.bloodType)
}

/**
 * As domain patients
 *
 * @return
 */
fun List<dbPatient>.asDomainPatients(): List<Patient> {
    var patientList = this.map {
        Patient(it.id, it.name, it.email, it.phoneNumber, it.dateOfBirth, it.gender, it.bloodType)
    }
    return patientList
}