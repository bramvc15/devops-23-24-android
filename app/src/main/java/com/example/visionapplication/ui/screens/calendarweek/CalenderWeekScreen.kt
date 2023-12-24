package com.example.visionapplication.ui.screens.calendarweek


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visionapplication.R
import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.shared.StatusBarColorUpdateEffect
import com.example.visionapplication.shared.displayText
import com.example.visionapplication.shared.getWeekPageTitle
import com.example.visionapplication.shared.rememberFirstVisibleWeekAfterScroll
import com.example.visionapplication.ui.screens.calendarweek.components.AppointmentItem
import com.example.visionapplication.ui.views.AppointmentViewModel
import com.example.visionapplication.ui.views.DoctorViewModel
import com.example.visionapplication.ui.views.TimeSlotViewModel
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val topAppColor: Color @Composable get() = colorResource(R.color.colorPrimary)


/**
 * Calendar week screen
 *
 * @param doctorViewModel
 * @param timeslotViewModel
 * @param appointmentViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarWeekScreen(doctorViewModel: DoctorViewModel,
                       timeslotViewModel : TimeSlotViewModel = viewModel(factory = TimeSlotViewModel.Factory),
                       appointmentViewModel: AppointmentViewModel = viewModel(factory = AppointmentViewModel.Factory)
) {
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    var selection by remember { mutableStateOf(currentDate) }
    val timeslots by timeslotViewModel.timeslots.collectAsState()
    val appointments by appointmentViewModel.appointments.collectAsState()
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedTimeSlot by remember { mutableStateOf(timeslots.filter { it.appointment != null }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val state = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = currentDate,
        )
        val visibleWeek = rememberFirstVisibleWeekAfterScroll(state)
        TopAppBar(
            modifier = Modifier.height(IntrinsicSize.Min),
            title = {
                Column {
                    Text(modifier = Modifier.testTag("maand"), text = getWeekPageTitle(visibleWeek), fontSize = 25.sp)
                    Text(
                        text = "${GlobalDoctor.doctor?.name}",
                        fontSize = 16.sp,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.colorPrimary),
                titleContentColor = colorResource(id = R.color.white),
            ),
            actions = {
                Box(modifier = Modifier
                    .wrapContentHeight()
                    .padding(end = 16.dp)
                    .clickable { dropdownExpanded = true }
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = MaterialTheme.shapes.small
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Dropdown",
                        modifier = Modifier.padding(4.dp),
                        tint = colorResource(id = R.color.white)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        doctorViewModel.doctors.collectAsState().value.forEach { doctor ->
                            DropdownMenuItem(
                                onClick = {
                                    timeslotViewModel.selectDoctor(doctor)
                                },
                                text = { Text(text = doctor.name) }
                            )
                        }
                    }
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

        selectedTimeSlot = timeslots.filter { LocalDate.parse(it.dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSS]]")) == selection  && it.appointment != null }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Appointments for ${selection.format(DateTimeFormatter.ofPattern("d MMMM"))}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            if (selectedTimeSlot.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(selectedTimeSlot) { timeslot ->
                        appointments.find { it.timeSlotId == timeslot.appointment?.timeSlotId }?.let {
                            AppointmentItem(
                                timeslot = timeslot,
                                appointment = it,
                                appointmentViewModel = appointmentViewModel,
                                onUpdateAppointment = { appointmentViewModel.updateAppointment(it) },
                                onUpdateTimeSlot = { timeslotViewModel.updateTimeSlot(it)},
                                onCancelAppointment = { appointmentViewModel.deleteAppointment(it) },
                            )
                        }
                    }
                }
            }else {
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
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("d")

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
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                color = if (isSelected) colorResource(R.color.noteColorYellow) else Color.White,
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(colorResource(R.color.noteColorYellow))
                    .align(Alignment.BottomCenter),
            )
        }
    }
}