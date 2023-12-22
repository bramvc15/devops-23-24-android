package com.example.templateapplication.ui.screens.calendarweek.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.model.TimeSlot

@Composable
fun EditAppointmentCard(
    timeslot: TimeSlot,
    newNote: String,
    newReason: String,
    onNoteChange: (String) -> Unit,
    onReasonChange: (String) -> Unit,
    onAppointmentTypeChange: (Int) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
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
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newNote,
                onValueChange = { onNoteChange(it) },
                label = { Text(text = "Notitie") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newReason,
                onValueChange = { onReasonChange(it) },
                label = { Text(text = "Reden") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppointmentTypeDropdown(timeslot, onAppointmentTypeSelected = { onAppointmentTypeChange(it) })

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onSaveClick()
                },
                colors = ButtonDefaults.buttonColors(Color.Green,contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Opslaan")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {
                          onCancelClick()
                },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Annuleren")
            }
        }
    }
}