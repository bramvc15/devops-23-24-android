package com.example.templateapplication.data

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.Patient
import com.example.templateapplication.network.AppointmentApiService

interface AppointmentRepository {
    suspend fun getAppointments(body: Patient): List<Appointment>
}

class NetworkAppointmentRepository(
    //private val appointmentDao: AppointmentDao,
    private val appointmentApiService: AppointmentApiService
): AppointmentRepository {
    override suspend fun getAppointments(body: Patient): List<Appointment> {
        return appointmentApiService.getAppointments(body)
    }
}