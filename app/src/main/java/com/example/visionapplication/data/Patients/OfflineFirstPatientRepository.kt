package com.example.visionapplication.data.Patients

import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.model.Patient
import com.example.visionapplication.network.PatientApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Offline first patient repository
 *
 * @property patientDao
 * @property patientApi
 * @constructor Create empty Offline first patient repository
 */
class OfflineFirstPatientRepository(private val patientDao: PatientDao, private val patientApi: PatientApiService) : PatientRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            updatePatientsInBackground()
        }
    }

    private suspend fun updatePatientsInBackground() {
        while (true) {
            refreshPatients()
            delay(300000)
        }
    }

    override fun getPatientsStream(): Flow<List<Patient>> {
        return patientDao.getAllPatients().map { patients -> patients.map { it.asDomainPatient() } }
            .onEach {
                if(it.isEmpty()) {
                    refreshPatients()
                }
            }
    }

    override fun getPatientStream(id: Int): Flow<Patient?> {
        return patientDao.getPatient(id).map { patient -> patient.asDomainPatient() }
    }

    override suspend fun refreshPatients() {
        patientApi.getPatients(GlobalDoctor.authedDoctor!!.bearerToken)
            .also { externalPatients -> patientDao.deleteAndInsert(patients = externalPatients.map(Patient::asDbPatient)) }
    }
}