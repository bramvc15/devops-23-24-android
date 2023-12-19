package com.example.templateapplication.data.Patients

import com.example.templateapplication.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    fun getPatientsStream(): Flow<List<Patient>>
    fun getPatientStream(id: Int): Flow<Patient?>
    suspend fun refreshPatients()
}