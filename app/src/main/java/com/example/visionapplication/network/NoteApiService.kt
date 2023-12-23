package com.example.visionapplication.network

import com.example.visionapplication.model.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

/**
 * Note api service
 *
 * @constructor Create empty Note api service
 */
interface NoteApiService {
    /**
     * Get notes
     *
     * @param headerValue
     * @return
     */
    @GET("note")
    suspend fun getNotes(
        @Header("Authorization") headerValue: String
    ): List<Note>

    /**
     * Update note
     *
     * @param headerValue
     * @param note
     * @return
     */
    @PUT("note")
    suspend fun updateNote(
        @Header("Authorization") headerValue: String,
       @Body note: Note
    ) : Note

    /**
     * Create note
     *
     * @param headerValue
     * @param note
     * @return
     */
    @POST("note")
    suspend fun createNote(
        @Header("Authorization") headerValue: String,
        @Body note: Note
    ) : Note

    /**
     * Delete note
     *
     * @param headerValue
     * @param note
     * @return
     */
    @HTTP(method = "DELETE", path = "note", hasBody = true)
    suspend fun deleteNote(
        @Header("Authorization") headerValue: String,
        @Body note: Note
    ) : Response<Unit>
}