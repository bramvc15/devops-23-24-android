package com.example.visionapplication.data.Appointments

import com.example.visionapplication.model.Appointment
import kotlinx.coroutines.flow.Flow

/**
 * Appointment repository
 *
 * @constructor Create empty Appointment repository
 */
interface AppointmentRepository {
    /**
     * Get appointments
     *
     * @return
     */
    fun getAppointments(): Flow<List<Appointment>>

    /**
     * Insert appointment
     *
     * @param appointment
     */
    suspend fun insertAppointment(appointment: Appointment)

    /**
     * Update appointment
     *
     * @param appointment
     */
    suspend fun updateAppointment(appointment: Appointment)

    /**
     * Delete appointment
     *
     * @param appointment
     */
    suspend fun deleteAppointment(appointment: Appointment)

    /**
     * Refresh appointments
     *
     */
    suspend fun refreshAppointments()

}