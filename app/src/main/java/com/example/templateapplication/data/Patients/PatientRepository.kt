package com.example.templateapplication.data.Patients

import com.example.templateapplication.model.Patient
import com.example.templateapplication.network.PatientApiService

interface PatientRepository {
    suspend fun getPatients(): List<Patient>
}

class NetworkPatientRepository(
    private val patientDao: PatientDao,
    private val patientApiService: PatientApiService
): PatientRepository {
    override suspend fun getPatients(): List<Patient> {
        return patientApiService.getPatients()
    }
}