package com.example.visionapplication.data.Doctors

import com.example.visionapplication.model.Doctor
import kotlinx.coroutines.flow.Flow

/**
 * Doctor repository
 *
 * @constructor Create empty Doctor repository
 */
interface DoctorRepository {
    /**
     * Get all doctors stream
     *
     * @return
     */
    fun getAllDoctorsStream(): Flow<List<Doctor>>

    /**
     * Get doctor stream
     *
     * @param id
     * @return
     */
    fun getDoctorStream(id: Int): Flow<Doctor?>

    /**
     * Refresh doctors
     *
     */
    suspend fun refreshDoctors()
}