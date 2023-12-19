package com.example.templateapplication.data.Doctors

import com.example.templateapplication.model.Doctor
import kotlinx.coroutines.flow.Flow

interface DoctorRepository {
    fun getAllDoctorsStream(): Flow<List<Doctor>>
    fun getDoctorStream(id: Int): Flow<Doctor?>
    suspend fun refreshDoctors()
}