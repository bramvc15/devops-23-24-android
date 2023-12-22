package com.example.templateapplication.ui.screens.calendarmonth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.data.GlobalDoctor
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.shared.clickable
import com.example.templateapplication.ui.components.CancelAppointmentDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private val appointmentField: Color @Composable get() = colorResource(R.color.white7)
private val informationColor: Color @Composable get() = colorResource(R.color.black)
private val toolbarColor: Color @Composable get() = colorResource(R.color.colorPrimary)
@Composable
fun LazyItemScope.AppointmentInformation(
    timeslot: TimeSlot,
    appointment: Appointment,
    onCancelAppointment: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillParentMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Box(
            modifier = Modifier
                .background(color = appointmentField)
                .weight(1f)
                .fillMaxHeight(),
        ) {
            AppointmentInformationDetails(timeslot, appointment, onCancelAppointment)
            Divider(color = toolbarColor)
        }

    }
}
@Composable
private fun AppointmentInformationDetails(
    timeslot: TimeSlot,
    appointment: Appointment,
    onCancelAppointment: () -> Unit
) {
    var isShowingOperations by remember { mutableStateOf(false) }
    var isShowingDeleteDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
                .fillMaxWidth()
                .clickable { isShowingOperations = !isShowingOperations },
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.parse(timeslot.dateTime))} - ${
                    DateTimeFormatter.ofPattern("HH:mm").format(
                        LocalDateTime.parse(timeslot.dateTime).plusMinutes(timeslot.duration.toLong()))}",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = informationColor
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = appointment.patient.name,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = informationColor
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = GlobalDoctor.doctor?.name ?: "N/A",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = informationColor
            )
        }
        if(isShowingOperations) {
            Button(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                onClick = {
                    isShowingDeleteDialog = true
                }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                )
            }
            if (isShowingDeleteDialog) {
                CancelAppointmentDialog(
                    onDismiss = { isShowingDeleteDialog = false },
                    onCancelAppointment = { onCancelAppointment() }
                )
            }
        }
    }
}