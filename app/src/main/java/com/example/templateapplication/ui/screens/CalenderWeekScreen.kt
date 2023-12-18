package com.example.templateapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.shared.StatusBarColorUpdateEffect
import com.example.templateapplication.shared.displayText
import com.example.templateapplication.shared.getWeekPageTitle
import com.example.templateapplication.shared.rememberFirstVisibleWeekAfterScroll
import com.example.templateapplication.ui.views.DoctorViewModel
import com.example.templateapplication.ui.views.TimeSlotViewModel
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val topAppColor: Color @Composable get() = colorResource(R.color.colorPrimary)

@Composable
fun CalendarWeekScreen(doctorViewModel: DoctorViewModel, timeslotViewModel : TimeSlotViewModel) {
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    var selection by remember { mutableStateOf(currentDate) }
    val timeslots by timeslotViewModel.timeslots.collectAsState()

    var isAddingAppointment by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("Consultation") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val state = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = currentDate,
        )
        val visibleWeek = rememberFirstVisibleWeekAfterScroll(state)
        TopAppBar(
            elevation = 0.dp,
            title = { Text(text = getWeekPageTitle(visibleWeek)) },
            actions = {
                Button(onClick = {
                    selection = LocalDate.now()
                }) {
                    Text(text = "Vandaag")
                }

                FloatingActionButton(
                    onClick = {
                        isAddingAppointment = true
                    }
                ) {
                    Icon(Icons.Filled.Add, "Add appointment")
                }
            },
        )
        StatusBarColorUpdateEffect(topAppColor)
        WeekCalendar(
            modifier = Modifier.background(color = colorResource(R.color.colorPrimary)),
            state = state,
            dayContent = { day ->
                Day(day.date, isSelected = selection == day.date) { clicked ->
                    if (selection != clicked) {
                        selection = clicked
                    }
                }
            },
        )
        val selectedAppointment = timeslots.filter { it.dateTime.toLocalDate() == selection }

        if(isAddingAppointment) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Maak een afspraak op ${selection.format(DateTimeFormatter.ofPattern("dd MMMM"))}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        label = { Text(text = "Naam") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        label = { Text(text = "Reden") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        label = { Text(text = "Note") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .clickable { dropdownExpanded = true }
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Spacer(modifier = Modifier.height(4.dp))
                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier = Modifier
                                .background(Color.White)
                        ) {
                            DropdownMenuItem(onClick = { selectedType = "Consultatie" }) {
                                Text(text = "Consultatie")
                            }
                            DropdownMenuItem(onClick = { selectedType = "Operatie"}) {
                                Text(text = "Operatie")
                            }
                        }
                        Text(selectedType, modifier = Modifier.padding(8.dp))
                    }

                    Button(onClick = {
                        isAddingAppointment = false
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(top = 16.dp)
                    ) {
                        Text(text = "Afspraak maken")
                    }



                }
            }
        }
        if (selectedAppointment.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Appointments for ${selection.format(DateTimeFormatter.ofPattern("dd MMMM"))}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(selectedAppointment) { appointment ->
                        AppointmentItem(timeslot = appointment, doctorViewModel = doctorViewModel)
                    }
                }
            }
        }else {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Appointments for ${selection.format(DateTimeFormatter.ofPattern("dd MMMM"))}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Happy Face"
                )
                Text(
                    text = "No appointments",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun AppointmentItem(timeslot: TimeSlot, doctorViewModel: DoctorViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = colorResource(id = R.color.lightgray )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "name: ${timeslot.appointmentDTO?.patientDTO?.name ?: "N/A"}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Appointment Time: ${timeslot.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Doctor: ${doctorViewModel.selectedDoctor?.name}",
                fontSize = 14.sp,
                color = Color.Black
            )

            // Add a clickable area to toggle expansion
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isExpanded = !isExpanded
                    }
            ) {
                Text(
                    text = if (isExpanded) "Click to collapse" else "Click for more details",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.example_7_yellow),
                    fontWeight = FontWeight.Bold,
                )
            }

            // Conditionally display additional information when expanded
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Reason: ${timeslot.appointmentDTO?.reason}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}


private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
private fun Day(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = date.dayOfWeek.displayText(),
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                color = if (isSelected) colorResource(R.color.example_7_yellow) else Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(colorResource(R.color.example_7_yellow))
                    .align(Alignment.BottomCenter),
            )
        }
    }
}