package com.example.templateapplication.data.Notes

import com.example.templateapplication.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotesStream(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun refreshNotes()
}
