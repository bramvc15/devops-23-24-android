package com.example.visionapplication.data.Notes

import com.example.visionapplication.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Note repository
 *
 * @constructor Create empty Note repository
 */
interface NoteRepository {
    /**
     * Get all notes stream
     *
     * @return
     */
    fun getAllNotesStream(): Flow<List<Note>>

    /**
     * Insert note
     *
     * @param note
     */
    suspend fun insertNote(note: Note)

    /**
     * Update note
     *
     * @param note
     */
    suspend fun updateNote(note: Note)

    /**
     * Delete note
     *
     * @param note
     */
    suspend fun deleteNote(note: Note)

    /**
     * Refresh notes
     *
     */
    suspend fun refreshNotes()
}
