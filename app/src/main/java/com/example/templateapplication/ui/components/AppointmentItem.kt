package com.example.templateapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.templateapplication.ui.views.TimeSlotViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentItem(
    timeslot: TimeSlot,
    appointmentViewModel: AppointmentViewModel,
    OnUpdateAppointment: (Appointment) -> Unit,
    OnDeleteTimeslot: (TimeSlot) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    val appointments by appointmentViewModel.appointments.collectAsState()
    val selectedAppointment by remember { mutableStateOf(appointments.find { it.timeSlotId == timeslot.appointment?.timeSlotId }) }

    var updatedAppointment by remember { mutableStateOf(selectedAppointment) }

    var newNoteTitle by remember { mutableStateOf("") }

    var dropdownExpanded by remember { mutableStateOf(false) }

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
                        text = "${LocalDateTime.parse(timeslot.dateTime).format(DateTimeFormatter.ofPattern("HH:mm"))} - ${LocalDateTime.parse(timeslot.dateTime).plusMinutes(timeslot.duration.toLong()).format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text = timeslot.appointment?.patient?.name ?: "N/A",
                        fontSize = 16.sp,
                        color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text = "Doctor: " + GlobalDoctor.doctor?.name,
                        fontSize = 14.sp,
                        color = Color.Black
                )

                // Conditionally display additional information when expanded
                if (isExpanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                            text = "Reason: ${timeslot.appointment?.reason}",
                            fontSize = 14.sp,
                            color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { isEditing = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                    ) {
                        Text(text = "Bewerk")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                            onClick = { OnDeleteTimeslot(timeslot) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                    ) {
                        Text(text = "Verwijder")
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
                    value = newNoteTitle,
                    onValueChange = { newNoteTitle = it },
                    label = { Text(text = "Notitie") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .align(Alignment.End)
                        .fillMaxHeight()
                ) {

                }

                Button(
                    onClick = {
                        isEditing = false
                        updatedAppointment = selectedAppointment?.copy(note = newNoteTitle)
                        OnUpdateAppointment(updatedAppointment!!)
                              },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green, contentColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Opslaan")
                }
            }
        }
    }
}