package com.example.visionapplication.data.Patients

import com.example.visionapplication.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    fun getPatientsStream(): Flow<List<Patient>>
    fun getPatientStream(id: Int): Flow<Patient?>
    suspend fun refreshPatients()
}