package com.example.templateapplication.network

import com.example.templateapplication.model.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface NoteApiService {
    @GET("note")
    suspend fun getNotes(): List<Note>

    @PUT("note")
    suspend fun updateNote(
       @Body note: Note
    ) : Note

    @POST("note")
    suspend fun createNote(
        @Body note: Note
    ) : Note

    // mag eigenlijk niet, zal later gefix worden danku victor XD
    @HTTP(method = "DELETE", path = "note", hasBody = true)
    suspend fun deleteNote(
        @Body note: Note
    ) : Response<Unit>
}