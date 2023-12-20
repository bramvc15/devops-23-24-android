package com.example.templateapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.data.GlobalDoctor
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.ui.views.AppointmentViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentItem(
    timeslot: TimeSlot,
    appointmentViewModel: AppointmentViewModel,
    onUpdateAppointment: (Appointment) -> Unit,
    onUpdateTimeSlot: (TimeSlot) -> Unit,
    onCancelAppointment: (Appointment) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    val appointments by appointmentViewModel.appointments.collectAsState()
    val selectedAppointment by remember { mutableStateOf(appointments.find { it.timeSlotId == timeslot.appointment?.timeSlotId }) }

    var updatedAppointment by remember { mutableStateOf(selectedAppointment) }
    var updatedTimeSlot by remember { mutableStateOf(timeslot) }

    var newNote by remember { mutableStateOf(selectedAppointment?.note ?: "") }
    var newReason by remember { mutableStateOf(selectedAppointment?.reason ?: "") }
    var newAppointmentType by remember { mutableIntStateOf(timeslot.appointmentType) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    if(!isEditing) {
        Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { isExpanded = !isExpanded },
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
                backgroundColor = colorResource(id = R.color.lightgray)
        ) {
            Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
            ) {
                Text(
                    text = timeslot.appointment?.patient?.name ?: "N/A",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text = "${LocalDateTime.parse(timeslot.dateTime).format(DateTimeFormatter.ofPattern("HH:mm"))} - ${LocalDateTime.parse(timeslot.dateTime).plusMinutes(timeslot.duration.toLong()).format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                )
                Text(
                        text = if(timeslot.appointmentType != 0) "Operation" else "Consultation",
                        fontSize = 13.sp,
                        color = Color.Black
                )

                if (isExpanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Duur: " + timeslot.duration.toString() + " minuten",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Doctor: " + GlobalDoctor.doctor?.name,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                            text = "Reason: ${timeslot.appointment?.reason}",
                            fontSize = 14.sp,
                            color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { isEditing = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                    ) {
                        Text(text = "Bewerk")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                            onClick = { showDeleteDialog = true },
                            colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.hsl(4f, 0.83f, 0.59f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                    ) {
                        Text(text = "Verwijder")
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
                                Text("Weet je zeker dat je deze afspraak wilt verwijderen?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showDeleteDialog = false
                                        onCancelAppointment(selectedAppointment!!)
                                    }
                                ) {
                                    Text("Verwijderen")
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        showDeleteDialog = false
                                    }
                                ) {
                                    Text("Annuleren")
                                }
                            }
                        )
                    }
                }
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium,
            backgroundColor = colorResource(id = R.color.lightgray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Bewerk afspraak",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))


                OutlinedTextField(
                    value = newNote,
                    onValueChange = { newNote = it },
                    label = { Text(text = "Notitie") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = newReason,
                    onValueChange = { newReason = it },
                    label = { Text(text = "Reden") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppointmentTypeDropdown(timeslot, onAppointmentTypeSelected = { newAppointmentType = it })

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        isEditing = false
                        updatedAppointment = selectedAppointment?.copy(
                            note = newNote,
                            reason = newReason
                        )

                        updatedTimeSlot = timeslot.copy(
                            appointmentType = newAppointmentType
                        )

                        onUpdateAppointment(updatedAppointment!!)
                        onUpdateTimeSlot(updatedTimeSlot)
                              },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.hsl(109f, 0.61f, 0.64f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Opslaan")
                }
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        isEditing = false
                        newNote = selectedAppointment?.note ?: ""
                        newReason = selectedAppointment?.reason ?: ""
                        newAppointmentType = timeslot.appointmentType
                              },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.hsl(4f, 0.83f, 0.59f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Annuleren")
                }
            }
        }
    }
}

@Composable
fun AppointmentTypeDropdown(
    TimeSlot: TimeSlot,
    onAppointmentTypeSelected: (Int) -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedAppointmentType by remember { mutableStateOf(TimeSlot.appointmentType) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .wrapContentHeight()
            .clickable { dropdownExpanded = true }
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.small
            )
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        DropdownMenu(
            modifier = Modifier
                .background(Color.White),
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
        ) {
            for (i in 0..1) {
                DropdownMenuItem(onClick = {
                    selectedAppointmentType = i
                    onAppointmentTypeSelected(i)
                    dropdownExpanded = false
                }) {
                    Text(text = if (i == 0) "Consultation" else "Operation")
                }
            }
        }
        Text(if(selectedAppointmentType == 0) "Consultation" else "Operation", modifier = Modifier.padding(10.dp), fontSize = 14.sp)
    }
}