package com.example.templateapplication.data.Doctors

import com.example.templateapplication.model.Doctor
import com.example.templateapplication.network.DoctorApiService

interface DoctorRepository {
    suspend fun getDoctors(token: String): List<Doctor>
    suspend fun insertDoctor(doctor: Doctor)
    suspend fun updateDoctor(doctor: Doctor) : Doctor
    suspend fun deleteDoctor(doctor: Doctor)
    suspend fun createDoctor(doctor: Doctor) : Doctor
}

class NetworkDoctorRepository(
    private val doctorDao: DoctorDao,
    private val doctorApiService: DoctorApiService
): DoctorRepository {
    override suspend fun getDoctors(token: String): List<Doctor> {
        val authorizationHeader = "Bearer $token"
        return doctorApiService.getDoctors(authorizationHeader)
    }

    override suspend fun insertDoctor(doctor: Doctor) {
        doctorDao.insert(doctor.asDbDoctor())
    }

    override suspend fun updateDoctor(doctor: Doctor): Doctor {
        return doctorApiService.updateDoctor(
            doctor
        )
    }

    override suspend fun deleteDoctor(doctor: Doctor) {
        doctorApiService.deleteDoctor(
            doctor
        )
    }

    override suspend fun createDoctor(doctor: Doctor): Doctor {
        return doctorApiService.createDoctor(
            doctor
        )
    }
}