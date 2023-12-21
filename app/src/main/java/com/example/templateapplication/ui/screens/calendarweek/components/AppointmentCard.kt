package com.example.templateapplication.ui.screens.calendarweek.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.templateapplication.model.TimeSlot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentCard(
    timeslot: TimeSlot,
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
                text = "${LocalDateTime.parse(timeslot.dateTime).format(DateTimeFormatter.ofPattern("HH:mm"))} - ${
                    LocalDateTime.parse(timeslot.dateTime).plusMinutes(timeslot.duration.toLong()).format(
                        DateTimeFormatter.ofPattern("HH:mm"))}",
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
                                    onCancelAppointment()
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
}