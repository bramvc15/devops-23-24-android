package com.example.templateapplication.model

import java.sql.Timestamp

data class TimeSlot(
    val id: Int,
    val duration: Int,
    val appointmentType: Int,
    val dateTime: Timestamp,
    val appointmentDTO: Appointment?,
    val doctorId: Int,
)
