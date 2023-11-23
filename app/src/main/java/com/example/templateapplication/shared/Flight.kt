package com.example.templateapplication.shared

import androidx.annotation.ColorRes
import com.example.templateapplication.R
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private typealias Airport = Flight.Airport

data class Flight(
    val time: LocalDateTime,
    val departure: Airport,
    @ColorRes val color: Int,
) {
    data class Airport(val city: String, val code: String)
}

fun generateFlights(): List<Flight> = buildList {
    val currentMonth = YearMonth.now()

    currentMonth.atDay(17).also { date ->
        add(
            Flight(
                date.atTime(14, 0),
                Airport("Lagos", "LOS"),
                R.color.black,
            ),
        )
        add(
            Flight(
                date.atTime(21, 30),
                Airport("Enugu", "ENU"),
                R.color.purple_200,
            ),
        )
    }

    currentMonth.atDay(22).also { date ->
        add(
            Flight(
                date.atTime(13, 20),
                Airport("Ibadan", "IBA"),
                R.color.purple_700,
            ),
        )
        add(
            Flight(
                date.atTime(17, 40),
                Airport("Sokoto", "SKO"),
                R.color.black,
            ),
        )
    }
    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(19, 0),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(19, 0),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(19, 15),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(19, 30),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(19, 40),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(19, 45),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(19, 50),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(3).also { date ->
        add(
            Flight(
                date.atTime(20, 0),
                Airport("Makurdi", "MDI"),
                R.color.black,
            ),
        )
    }

    currentMonth.atDay(12).also { date ->
        add(
            Flight(
                date.atTime(18, 15),
                Airport("Kaduna", "KAD"),
                R.color.purple_700,
            ),
        )
    }

    currentMonth.plusMonths(1).atDay(13).also { date ->
        add(
            Flight(
                date.atTime(7, 30),
                Airport("Kano", "KAN"),
                R.color.black,
            ),
        )
        add(
            Flight(
                date.atTime(10, 50),
                Airport("Minna", "MXJ"),
                R.color.purple_200,
            ),
        )
    }

    currentMonth.minusMonths(1).atDay(9).also { date ->
        add(
            Flight(
                date.atTime(20, 15),
                Airport("Asaba", "ABB"),
                R.color.purple_200,
            ),
        )
    }
}

val flightDateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("EEE'\n'dd MMM'\n'HH:mm")