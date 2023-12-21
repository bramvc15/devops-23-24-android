package com.example.templateapplication.data.Doctors

import android.util.Log
import com.example.templateapplication.data.GlobalDoctor
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.network.DoctorApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OfflineFirstDoctorRepository(private val doctorDao: DoctorDao, private val doctorApi: DoctorApiService) : DoctorRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            updateDoctorsInBackground()
        }
    }

    private suspend fun updateDoctorsInBackground() {
        while (true) {
            delay(300000)
            refreshDoctors()
        }

    }

    override fun getAllDoctorsStream(): Flow<List<Doctor>> {
        return doctorDao.getAllDoctors().map { doctors -> doctors.map { it.asDomainDoctor() } }
            .onEach {
                if (it.isEmpty()) {
                    refreshDoctors()
                }
            }
    }

    override fun getDoctorStream(id: Int): Flow<Doctor?> {
        return doctorDao.getDoctor(id).map { doc ->  doc?.asDomainDoctor() }
    }

    override suspend fun refreshDoctors() {
        doctorApi.getDoctors(GlobalDoctor.authedDoctor!!.bearerToken)
            .also { externalDoctors -> doctorDao.deleteAndInsert(doctors = externalDoctors.map(Doctor::asDbDoctor)) }
    }
}