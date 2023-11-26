package com.example.templateapplication.shared

import com.example.templateapplication.models.Note
import java.time.YearMonth
import java.time.format.DateTimeFormatter


fun generateNotes(): List<Note> = buildList {

    val currentMonth = YearMonth.now()

    currentMonth.atDay(26).also { date ->
        add(
            Note(
               1,
                "test",
                "test",
                3L
            ),
        )
        add(
            Note(
                2,
                "test2",
                "test2",
                3L
            ),
        )
    }

}

val noteDateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("EEE'\n'dd MMM'\n'HH:mm")