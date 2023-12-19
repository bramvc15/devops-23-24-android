package com.example.templateapplication.data.Appointments

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.network.AppointmentApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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
        appointmentApi.createAppointment(appointment)
    }

    override suspend fun updateAppointment(appointment: Appointment) {
        appointmentDao.update(appointment.asDbAppointment())
        appointmentApi.updateAppointment(appointment)
    }

    override suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.delete(appointment.asDbAppointment())
        appointmentApi.deleteAppointment(appointment)
    }

    override suspend fun refreshAppointments() {
        appointmentApi.getAppointments()
            .also { externalAppointments -> appointmentDao.deleteAndInsert(appointments = externalAppointments.map(Appointment::asDbAppointment)) }
    }
}