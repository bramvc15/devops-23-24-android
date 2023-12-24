package com.example.visionapplication.data.Appointments

import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.model.Appointment
import com.example.visionapplication.network.AppointmentApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Offline first appointment repository
 *
 * @property appointmentDao
 * @property appointmentApi
 * @constructor Create empty Offline first appointment repository
 */
class OfflineFirstAppointmentRepository(private val appointmentDao: AppointmentDao, private val appointmentApi: AppointmentApiService): AppointmentRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            updateAppointmentsInBackground()
        }
    }

    private suspend fun updateAppointmentsInBackground() {
        while (true) {
            refreshAppointments()
            delay(300000)
        }
    }

    override fun getAppointments(): Flow<List<Appointment>> {
        return appointmentDao.getAllAppointments().map { appointments -> appointments.map { it.asDomainAppointment() } }
            .onEach {
                if (it.isEmpty()) {
                    refreshAppointments()
                }
            }
    }

    override suspend fun insertAppointment(appointment: Appointment) {
        appointmentDao.insert(appointment.asDbAppointment())
        appointmentApi.createAppointment(GlobalDoctor.authedDoctor!!.bearerToken, appointment)
    }

    override suspend fun updateAppointment(appointment: Appointment) {
        appointmentDao.update(appointment.asDbAppointment())
        appointmentApi.updateAppointment(GlobalDoctor.authedDoctor!!.bearerToken, appointment)
    }

    override suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.delete(appointment.asDbAppointment())
        appointmentApi.deleteAppointment(GlobalDoctor.authedDoctor!!.bearerToken, appointment)
    }

    override suspend fun refreshAppointments() {
        appointmentApi.getAppointments(GlobalDoctor.authedDoctor!!.bearerToken)
            .also { externalAppointments -> appointmentDao.deleteAndInsert(appointments = externalAppointments.map(Appointment::asDbAppointment)) }
    }
}