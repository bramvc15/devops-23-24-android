package com.example.visionapplication.data.Appointments

import com.example.visionapplication.model.Appointment
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    fun getAppointments(): Flow<List<Appointment>>
    suspend fun insertAppointment(appointment: Appointment)
    suspend fun updateAppointment(appointment: Appointment)
    suspend fun deleteAppointment(appointment: Appointment)
    suspend fun refreshAppointments()

}