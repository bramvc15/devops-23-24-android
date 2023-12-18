package com.example.templateapplication.data

import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.model.PatientDTO
import com.example.templateapplication.network.AppointmentApiService

interface AppointmentRepository {
    suspend fun getAppointments(body: PatientDTO): List<Appointment>
}

class NetworkAppointmentRepository(
    //private val appointmentDao: AppointmentDao,
    private val appointmentApiService: AppointmentApiService
): AppointmentRepository {
    override suspend fun getAppointments(body: PatientDTO): List<Appointment> {
        return appointmentApiService.getAppointments(body)
    }
}