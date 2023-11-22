package com.example.templateapplication.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.shared.Flight
import com.example.templateapplication.shared.SimpleCalendarTitle
import com.example.templateapplication.shared.StatusBarColorUpdateEffect
import com.example.templateapplication.shared.displayText
import com.example.templateapplication.shared.flightDateTimeFormatter
import com.example.templateapplication.shared.generateFlights
import com.example.templateapplication.shared.rememberFirstCompletelyVisibleMonth
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
import java.time.YearMonth
import java.util.Locale

private val flights = generateFlights().groupBy { it.time.toLocalDate() }

private val pageBackgroundColor: Color @Composable get() = colorResource(R.color.teal_200)
private val itemBackgroundColor: Color @Composable get() = colorResource(R.color.white)
private val toolbarColor: Color @Composable get() = colorResource(R.color.black)
private val selectedItemColor: Color @Composable get() = colorResource(R.color.purple_700)
private val inActiveTextColor: Color @Composable get() = colorResource(R.color.purple_200)

@Composable
fun CalenderScreen() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }
    val flightsInSelectedDate = remember {
        derivedStateOf {
            val date = selection?.date
            if (date == null) emptyList() else flights[date].orEmpty()
        }
    }
    StatusBarColorUpdateEffect(toolbarColor)
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(pageBackgroundColor),
            .background(Color.LightGray)
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
            // Clear selection if we scroll to a new month.
            selection = null
        }

        // Draw light content on dark background.
        CompositionLocalProvider(LocalContentColor provides darkColors().onSurface) {
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
                            flights[day.date].orEmpty().map { colorResource(it.color) }
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
                        modifier = Modifier.padding(vertical = 8.dp),
                        daysOfWeek = daysOfWeek,
                    )
                },
            )
            Divider(color = pageBackgroundColor)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = flightsInSelectedDate.value) { flight ->
                    FlightInformation(flight)
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
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) selectedItemColor else Color.Gray,
            )
            .padding(1.dp)
            .background(color = itemBackgroundColor)
           // .background(color = Color.Gray)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> inActiveTextColor
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 4.dp),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 12.sp,
        )
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
    }
}

// dit is de rij met de dagen van de week
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
                color = Color.Black,
                text = dayOfWeek.displayText(uppercase = true),
                fontWeight = FontWeight.Black,
            )
        }
    }
}

@Composable
private fun LazyItemScope.FlightInformation(flight: Flight) {
    Row(
        modifier = Modifier
            .fillParentMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Box(
            modifier = Modifier
                .background(color = colorResource(flight.color))
                .fillParentMaxWidth(1 / 7f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = flightDateTimeFormatter.format(flight.time).uppercase(Locale.ENGLISH),
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = 12.sp,
            )
        }
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(1f)
                .fillMaxHeight(),
        ) {
            AirportInformation(flight.departure, isDeparture = true)
        }
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(1f)
                .fillMaxHeight(),
        ) {
            AirportInformation(flight.destination, isDeparture = false)
        }
    }
    Divider(color = pageBackgroundColor, thickness = 2.dp)
}
// deze functie is voor de velden op de dag the bekijken
@Composable
private fun AirportInformation(airport: Flight.Airport, isDeparture: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        val resource = if (isDeparture) {
            R.drawable.oog
        } else {
            R.drawable.oog
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterEnd,
        ) {
           Image(painter = painterResource(resource), contentDescription = null)
        }
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = airport.code,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = airport.city,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

// The default dark them ripple is too bright so we tone it down.
private object Example3RippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(Color.Gray, lightTheme = false)

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(Color.Gray, lightTheme = false)
}
