package com.example.templateapplication.data.Doctors

import com.example.templateapplication.model.Doctor
import com.example.templateapplication.network.DoctorApiService

interface DoctorRepository {
    suspend fun getDoctors(): List<Doctor>

    /**
     * Insert doctor in the data source
     */
    suspend fun insertDoctor(doctor: Doctor)
}

class NetworkDoctorRepository(
    private val doctorDao: DoctorDao,
    private val doctorApiService: DoctorApiService
): DoctorRepository {
    override suspend fun getDoctors(): List<Doctor> {
        return doctorApiService.getDoctors()
    }

    override suspend fun insertDoctor(doctor: Doctor) {
        doctorDao.insert(doctor.asDbDoctor())
    }
}