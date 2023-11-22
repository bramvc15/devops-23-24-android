package com.example.templateapplication.data

import com.example.templateapplication.models.Doctor
import com.example.templateapplication.network.DoctorApiService
import com.example.templateapplication.network.asDomainObjects

interface DoctorsRepository {
    suspend fun getDoctors(): List<Doctor>
}

class ApiDoctorsRepository(
    private val doctorApiService: DoctorApiService
): DoctorsRepository {
    override suspend fun getDoctors(): List<Doctor> {
        return doctorApiService.getDoctors().asDomainObjects()
    }
}