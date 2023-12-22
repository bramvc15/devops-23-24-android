package com.example.templateapplication.ui.screens.calendarweek.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.data.GlobalDoctor
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.TimeSlot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentCard(
    timeslot: TimeSlot,
    appointment: Appointment,
    onEdit: () -> Unit,
    onCancelAppointment: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = appointment.patient.name,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${LocalDateTime.parse(timeslot.dateTime).format(DateTimeFormatter.ofPattern("HH:mm"))} - ${
                    LocalDateTime.parse(timeslot.dateTime).plusMinutes(timeslot.duration.toLong()).format(
                        DateTimeFormatter.ofPattern("HH:mm"))}",
                fontSize = 16.sp,
            )
            Text(
                text = if(timeslot.appointmentType != 0) "Operation" else "Consultation",
                fontSize = 13.sp,
            )

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Duur: " + timeslot.duration.toString() + " minuten",
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Doctor: " + GlobalDoctor.doctor?.name,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Reden: ${appointment.reason}",
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Notitie: ${appointment.note ?: "N/A"}",
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onEdit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Bewerk")
                }
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Annuleer")
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDeleteDialog = false
                        },
                        title = {
                            Text("Waarschuwing")
                        },
                        text = {
                            Text("Weet je zeker dat je deze afspraak wilt annuleren?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDeleteDialog = false
                                    onCancelAppointment()
                                }
                            ) {
                                Text("Ja")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showDeleteDialog = false
                                }
                            ) {
                                Text("Nee")
                            }
                        }
                    )
                }
            }
        }
    }
}