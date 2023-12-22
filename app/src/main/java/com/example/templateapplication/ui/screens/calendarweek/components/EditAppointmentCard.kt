package com.example.templateapplication.ui.screens.calendarweek.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
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
                          onCancelClick()
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