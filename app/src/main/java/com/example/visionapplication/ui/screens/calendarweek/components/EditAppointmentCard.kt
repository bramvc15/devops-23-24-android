package com.example.visionapplication.ui.screens.calendarweek.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visionapplication.model.TimeSlot

/**
 * Edit appointment card
 *
 * @param timeslot
 * @param newNote
 * @param newReason
 * @param onNoteChange
 * @param onReasonChange
 * @param onAppointmentTypeChange
 * @param onSaveClick
 * @param onCancelClick
 * @receiver
 * @receiver
 * @receiver
 * @receiver
 * @receiver
 */
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
    Text(
        text = "Edit appointment",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = newNote,
        onValueChange = { onNoteChange(it) },
        label = { Text(text = "Note") },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = newReason,
        onValueChange = { onReasonChange(it) },
        label = { Text(text = "Reason") },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    AppointmentTypeDropdown(timeslot, onAppointmentTypeSelected = { onAppointmentTypeChange(it) })

    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            onSaveClick()
        },
        colors = ButtonDefaults.buttonColors(contentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Save")
    }
    Spacer(modifier = Modifier.height(4.dp))
    Button(
        onClick = {
                  onCancelClick()
        },
        colors = ButtonDefaults.buttonColors(contentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Cancel")
    }
}

/**
 * Appointment type dropdown
 *
 * @param timeSlot
 * @param onAppointmentTypeSelected
 * @receiver
 */
@Composable
fun AppointmentTypeDropdown(
    timeSlot: TimeSlot,
    onAppointmentTypeSelected: (Int) -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedAppointmentType by remember { mutableIntStateOf(timeSlot.appointmentType) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(1.dp))
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.small)
            .clickable { dropdownExpanded = true }
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
        ) {
            for (i in 0..1) {
                DropdownMenuItem(
                    onClick = {
                        selectedAppointmentType = i
                        onAppointmentTypeSelected(i)
                        dropdownExpanded = false
                    },
                    text = { Text(text = if (i == 0) "Consultation" else "Operation") },
                )
            }
        }
        Text(if(selectedAppointmentType == 0) "Consultation" else "Operation", modifier = Modifier.padding(10.dp), fontSize = 14.sp)
    }
}