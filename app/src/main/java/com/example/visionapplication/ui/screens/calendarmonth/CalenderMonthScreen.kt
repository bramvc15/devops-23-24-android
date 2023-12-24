package com.example.visionapplication.ui.screens.calendarmonth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visionapplication.R
import com.example.visionapplication.shared.SimpleCalendarTitle
import com.example.visionapplication.shared.StatusBarColorUpdateEffect
import com.example.visionapplication.shared.displayText
import com.example.visionapplication.shared.rememberFirstCompletelyVisibleMonth
import com.example.visionapplication.ui.screens.calendarmonth.components.AppointmentInformation
import com.example.visionapplication.ui.views.AppointmentViewModel
import com.example.visionapplication.ui.views.TimeSlotViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
private val toolbarColor: Color @Composable get() = colorResource(R.color.colorPrimary)
private val topAppColor: Color @Composable get() = colorResource(R.color.colorPrimary)
private val backgroundColor: Color @Composable get() = colorResource(R.color.white)
private val daysOfweekFontColor: Color @Composable get() = colorResource(R.color.noteColorPink)
private val daysColor: Color @Composable get() = colorResource(R.color.dark_gray)
private val selectedItemColor: Color @Composable get() = colorResource(R.color.noteColorYellow)
private val appointmentField: Color @Composable get() = colorResource(R.color.white7)
private val informationColor: Color @Composable get() = colorResource(R.color.black)


/**
 * Calender month screen
 *
 * @param timeslotViewModel
 * @param appointmentViewModel
 */
@Composable
fun CalenderMonthScreen(
    timeslotViewModel : TimeSlotViewModel = viewModel(factory = TimeSlotViewModel.Factory),
    appointmentViewModel: AppointmentViewModel = viewModel(factory = AppointmentViewModel.Factory),
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }

    val timeslots by timeslotViewModel.timeslots.collectAsState()
    val appointments by appointmentViewModel.appointments.collectAsState()

    val appointmentsInSelectedDate = remember {
        derivedStateOf {
            val date = selection?.date
            if (date == null) emptyList() else timeslots.filter { LocalDateTime.parse(it.dateTime).toLocalDate() == date && it.appointment != null }
        }
    }

    StatusBarColorUpdateEffect(topAppColor)
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfGrid,
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstCompletelyVisibleMonth(state)
        LaunchedEffect(visibleMonth) {
            selection = null
        }

        CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {
            SimpleCalendarTitle(
                modifier = Modifier
                    .background(toolbarColor)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                    }
                },
            )
            HorizontalCalendar(
                modifier = Modifier.wrapContentWidth(),
                state = state,
                dayContent = { day ->
                    CompositionLocalProvider(LocalRippleTheme provides Example3RippleTheme) {
                        val colors = if (day.position == DayPosition.MonthDate) {
                            timeslots.filter { it.appointment != null && LocalDateTime.parse(it.dateTime).toLocalDate() == day.date }.map { colorResource(R.color.purple_200) }
                        } else {
                            emptyList()
                        }
                        Day(
                            day = day,
                            isSelected = selection == day,
                            colors = colors,
                        ) { clicked ->
                            selection = clicked
                        }
                    }
                },
                monthHeader = {
                    MonthHeader(
                        modifier = Modifier
                            .background(
                                color = colorResource(
                                    id = R.color.colorPrimary
                                )
                            ),
                        daysOfWeek = daysOfWeek,
                    )
                },
            )

            selection?.let { selectedDate ->
                if(appointmentsInSelectedDate.value.isEmpty()){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "No appointments on ${selectedDate.date.format(DateTimeFormatter.ofPattern("dd MMMM"))}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Appointments on ${
                                selectedDate.date.format(
                                    DateTimeFormatter.ofPattern(
                                        "dd MMMM"
                                    )
                                )
                            }",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = appointmentsInSelectedDate.value) { timeslot ->
                    appointments.find { it.timeSlotId == timeslot.appointment?.timeSlotId }?.let { appointment ->
                        AppointmentInformation(
                            timeslot = timeslot,
                            appointment = appointment,
                            onCancelAppointment = {
                                appointmentViewModel.deleteAppointment(timeslot.appointment!!)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    colors: List<Color> = emptyList(),
    onClick: (CalendarDay) -> Unit = {},
) {

    val isDarkTheme = isSystemInDarkTheme()

    val boxBackgroundColor = if (isDarkTheme) {
        colorResource(id = R.color.lightgray)
    } else {
        colorResource(id = R.color.calLight)
    }

    val boxBackgroundColorBorder = if (isDarkTheme) {
        colorResource(id = R.color.black)
    } else {
        colorResource(id = R.color.white)
    }

    Box(
        modifier = Modifier
            .background(boxBackgroundColor)
            .aspectRatio(1f)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) colorResource(id = R.color.noteColorPink) else boxBackgroundColorBorder)
            .padding(1.dp)
            .background(color = daysColor)


            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> MaterialTheme.colorScheme.onPrimaryContainer
            DayPosition.InDate, DayPosition.OutDate -> colorResource(id = R.color.dark_gray)
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            for (color in colors) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(color),
                )
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 4.dp),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.White,
                text = dayOfWeek.displayText(uppercase = true),
            )
        }
    }
}

private object Example3RippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(Color.Gray, lightTheme = false)

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(Color.Gray, lightTheme = false)
}