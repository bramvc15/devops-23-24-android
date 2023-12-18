package com.example.templateapplication.network

import com.example.templateapplication.model.Note
import retrofit2.http.GET

interface NoteApiService {
    @GET("note")
    suspend fun getNotes(): List<Note>
}