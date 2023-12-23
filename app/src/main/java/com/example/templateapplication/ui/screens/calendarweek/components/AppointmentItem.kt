package com.example.templateapplication.ui.screens.calendarweek.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.ui.views.AppointmentViewModel

@Composable
fun AppointmentItem(
    timeslot: TimeSlot,
    appointment: Appointment,
    appointmentViewModel: AppointmentViewModel,
    onUpdateAppointment: (Appointment) -> Unit,
    onUpdateTimeSlot: (TimeSlot) -> Unit,
    onCancelAppointment: (Appointment) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }

    val appointments by appointmentViewModel.appointments.collectAsState()
    val selectedAppointment by remember { mutableStateOf(appointments.find { it.timeSlotId == timeslot.appointment?.timeSlotId }) }

    var updatedAppointment by remember { mutableStateOf(selectedAppointment) }
    var updatedTimeSlot by remember { mutableStateOf(timeslot) }

    var newNote by remember { mutableStateOf(selectedAppointment?.note ?: "") }
    var newReason by remember { mutableStateOf(selectedAppointment?.reason ?: "") }
    var newAppointmentType by remember { mutableIntStateOf(timeslot.appointmentType) }

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (!isEditing) {
                AppointmentCard(
                    timeslot = timeslot,
                    appointment = appointment,
                    onEdit = {
                        isEditing = true
                    },
                    onCancelAppointment = {
                        onCancelAppointment(appointment)
                    },
                    isExpanded = isExpanded
                )
            } else {
                EditAppointmentCard(
                    timeslot = timeslot,
                    newNote = newNote,
                    newReason = newReason,
                    onNoteChange = { newNote = it },
                    onReasonChange = { newReason = it },
                    onAppointmentTypeChange = { newAppointmentType = it },
                    onSaveClick = {
                        isEditing = false
                        updatedAppointment = appointment.copy(
                            note = newNote,
                            reason = newReason
                        )

                        updatedTimeSlot = timeslot.copy(
                            appointmentType = newAppointmentType
                        )

                        onUpdateAppointment(updatedAppointment!!)
                        onUpdateTimeSlot(updatedTimeSlot)
                    },
                    onCancelClick = {
                        isEditing = false
                        newNote = appointment.note ?: ""
                        newReason = appointment.reason
                        newAppointmentType = timeslot.appointmentType
                    })
            }

        }
    }
}