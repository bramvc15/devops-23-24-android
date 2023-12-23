package com.example.templateapplication.ui.screens.calendarweek.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.data.GlobalDoctor
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.ui.components.CancelAppointmentDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentCard(
    timeslot: TimeSlot,
    appointment: Appointment,
    isExpanded: Boolean,
    onEdit: () -> Unit,
    onCancelAppointment: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val ctx = LocalContext.current

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
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
            val u = Uri.parse("tel:" + appointment.patient.phoneNumber)
            val i = Intent(Intent.ACTION_DIAL, u)
            try {
                ctx.startActivity(i)
            } catch (s: SecurityException) {
                Toast.makeText(ctx, "Er is een fout opgetreden", Toast.LENGTH_LONG).show()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Phone",
                tint = Color.White
            )
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
            CancelAppointmentDialog(
                onDismiss = { showDeleteDialog = false },
                onCancelAppointment = { onCancelAppointment() }
            )
        }
    }
}
