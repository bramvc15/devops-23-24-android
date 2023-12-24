package com.example.visionapplication.data.Appointments

import com.example.visionapplication.model.Appointment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAppointmentRepository(private val appointmentList : MutableList<Appointment>) : AppointmentRepository {
    override fun getAppointments(): Flow<List<Appointment>> = flow {
        emit(appointmentList)
    }

    override suspend fun insertAppointment(appointment: Appointment) {
    }

    override suspend fun updateAppointment(appointment: Appointment) {

        appointmentList.remove(appointmentList[0])
        appointmentList.add(appointment)
    }

    override suspend fun deleteAppointment(appointment: Appointment) {
        appointmentList.remove(appointment)
    }

    override suspend fun refreshAppointments() {
    }
}