package com.example.visionapplication.ui.screens.calendarweek.components

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.model.Appointment
import com.example.visionapplication.model.TimeSlot
import com.example.visionapplication.ui.components.CancelAppointmentDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Appointment card
 *
 * @param timeslot
 * @param appointment
 * @param isExpanded
 * @param onEdit
 * @param onCancelAppointment
 * @receiver
 * @receiver
 */
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
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                    append("Duration: ")
                }
                append("${timeslot.duration} minutes")
            },
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                    append("Doctor: ")
                }
                append(GlobalDoctor.doctor?.name)
            },
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                    append("Reason: ")
                }
                append(appointment.reason)
            },
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                    append("Note: ")
                }
                append(appointment.note ?: "")
            },
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onEdit() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Edit")
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
                Toast.makeText(ctx, "An error occured", Toast.LENGTH_LONG).show()
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
            Text(text = "Cancel")
        }

        if (showDeleteDialog) {
            CancelAppointmentDialog(
                onDismiss = { showDeleteDialog = false },
                onCancelAppointment = { onCancelAppointment() }
            )
        }
    }
}
