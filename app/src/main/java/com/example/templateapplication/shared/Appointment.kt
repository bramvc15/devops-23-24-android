package com.example.templateapplication.shared

import androidx.annotation.ColorRes
import com.example.templateapplication.R
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private typealias Doctor = Appointment.Doctor
private typealias Patient = Appointment.Patient

data class Appointment(
    val patient: Patient,
    val time: LocalDateTime,
    val doctor: Doctor,
    val reason: String,
    @ColorRes val color: Int,
) {
    data class Doctor(val name: String)
    data class Patient(val name: String, val age: Int)
}

fun generateAppointments(): List<Appointment> = buildList {
    val currentMonth = YearMonth.now()

    currentMonth.atDay(26).also { date ->
        add(
            Appointment(
                Patient("Mike Smith", 42),
                date.atTime(14, 0),
                Doctor("Dr. Lee"),
                "jeukend oog",
                R.color.colorPrimary
            ),
        )
        add(
            Appointment(
                Patient("Mike Smith", 42),
                date.atTime(15, 0),
                Doctor("Dr. Lee"),
                "jeukend oog",
                R.color.colorPrimary
            ),
        )
    }
}

val appointmentDateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("EEE'\n'dd MMM'\n'HH:mm")