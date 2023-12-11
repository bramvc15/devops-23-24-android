package com.example.templateapplication.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Serializable
data class TimeSlot(
    val id: Int,
    val appointmentType: Int,
    @Serializable(with = LocalDateTimeSerializer::class) val dateTime: LocalDateTime,
    val duration: Int,
    val appointmentDTO: Appointment?
)

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : kotlinx.serialization.KSerializer<LocalDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(formatter.format(value))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }
}
