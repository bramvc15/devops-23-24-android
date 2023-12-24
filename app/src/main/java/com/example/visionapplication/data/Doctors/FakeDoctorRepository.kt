package com.example.visionapplication.data.Doctors

import com.example.visionapplication.model.Doctor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDoctorRepository(private val  doctors : MutableList<Doctor>) : DoctorRepository{
    override fun getAllDoctorsStream(): Flow<List<Doctor>> = flow  {
        emit(doctors)
    }

    override fun getDoctorStream(id: Int): Flow<Doctor?> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshDoctors() {
    }

}