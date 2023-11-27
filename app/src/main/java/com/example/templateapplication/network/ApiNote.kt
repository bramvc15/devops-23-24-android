package com.example.templateapplication.network

import com.example.templateapplication.model.Note

data class ApiNote(
    val id: Int?,
    val title: String,
    val content: String,
    val timestamp: Long
)

fun List<ApiNote>.asDomainObjects(): List<Note> {
    val domainList = this.map {
        Note(it.id, it.title, it.content, it.timestamp)
    }
    return domainList
}