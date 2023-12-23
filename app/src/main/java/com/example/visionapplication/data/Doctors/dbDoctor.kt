package com.example.visionapplication.data.Doctors

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.visionapplication.model.Doctor

/**
 * Db doctor
 *
 * @property id
 * @property name
 * @property specialization
 * @property gender
 * @property biograph
 * @property isAvailable
 * @property imageLink
 * @property auth0Id
 * @constructor Create empty Db doctor
 */
@Entity(tableName = "doctors")
data class dbDoctor(
    @PrimaryKey
    val id: Int,
    val name: String,
    val specialization: String,
    val gender: Int,
    val biograph: String?,
    val isAvailable: Boolean,
    val imageLink: String,
    val auth0Id: String,
)

/**
 * As domain doctor
 *
 * @return
 */
fun dbDoctor.asDomainDoctor(): Doctor {
    return Doctor(id = this.id,
        name = this.name,
        specialization = this.specialization,
        gender = this.gender,
        biograph = this.biograph,
        isAvailable = this.isAvailable,
        imageLink = this.imageLink,
        auth0Id = this.auth0Id)
}

/**
 * As db doctor
 *
 * @return
 */
fun Doctor.asDbDoctor(): dbDoctor {
    return dbDoctor(id = this.id,
        name = this.name,
        specialization = this.specialization,
        gender = this.gender,
        biograph = this.biograph,
        isAvailable = this.isAvailable,
        imageLink = this.imageLink,
        auth0Id = this.auth0Id)
}

/**
 * As domain doctors
 *
 * @return
 */
fun List<dbDoctor>.asDomainDoctors(): List<Doctor> {
    var doctorList = this.map {
        Doctor(it.id, it.name, it.specialization, it.gender, it.biograph, it.isAvailable, it.imageLink, it.auth0Id)
    }
    return doctorList
}