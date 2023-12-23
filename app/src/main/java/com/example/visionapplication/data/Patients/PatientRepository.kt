package com.example.visionapplication.data.Patients

import com.example.visionapplication.model.Patient
import kotlinx.coroutines.flow.Flow

/**
 * Patient repository
 *
 * @constructor Create empty Patient repository
 */
interface PatientRepository {
    /**
     * Get patients stream
     *
     * @return
     */
    fun getPatientsStream(): Flow<List<Patient>>

    /**
     * Get patient stream
     *
     * @param id
     * @return
     */
    fun getPatientStream(id: Int): Flow<Patient?>

    /**
     * Refresh patients
     *
     */
    suspend fun refreshPatients()
}