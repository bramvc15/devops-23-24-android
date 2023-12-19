package com.example.templateapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.ui.views.DoctorViewModel
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentItem(timeslot: TimeSlot, doctorViewModel: DoctorViewModel) {
    var isExpanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
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
                        text = "${timeslot.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text = "${timeslot.appointment?.patient?.name ?: "N/A"}",
                        fontSize = 16.sp,
                        color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text = "Doctor:",
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
                    Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                    ) {
                        Text(text = "Bewerk")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                    ) {
                        Text(text = "Annuleer")
                    }
                }
            }
        }
    } else {

    }
}