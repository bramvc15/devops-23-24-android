package com.example.templateapplication.data.Notes

import com.example.templateapplication.model.Note
import com.example.templateapplication.network.NoteApiService

interface NoteRepository {
    suspend fun getNotes(): List<Note>
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note) : Note
    suspend fun deleteNote(note: Note)
    suspend fun createNote(note: Note) : Note
}

class NetworkNoteRepository(
    private val noteDao: NoteDao,
    private val noteApiService: NoteApiService,
): NoteRepository {
    override suspend fun getNotes(): List<Note> {
        return noteApiService.getNotes()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insert(note.asDbNote())
    }

    override suspend fun updateNote(note: Note): Note {
        return noteApiService.updateNote(
            note
        )
    }

    override suspend fun deleteNote(note: Note) {
        noteApiService.deleteNote(
            note
        )
    }

    override suspend fun createNote(note: Note): Note {
        return noteApiService.createNote(
            note
        )
    }


}
