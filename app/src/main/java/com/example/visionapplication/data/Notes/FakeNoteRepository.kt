package com.example.visionapplication.data.Notes

import com.example.visionapplication.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository(private val notesList : MutableList<Note>) : NoteRepository {

    override fun getAllNotesStream(): Flow<List<Note>>  = flow {
        emit(notesList)
    }

    override suspend fun insertNote(note: Note) {
        notesList.add(note)
    }

    override suspend fun updateNote(note: Note) {
    }

    override suspend fun deleteNote(note: Note) {
        notesList.remove(note)
    }

    override suspend fun refreshNotes() {
    }
}