package com.example.templateapplication.data.Notes

import com.example.templateapplication.model.Note
import com.example.templateapplication.network.NoteApiService

interface NoteRepository {
    suspend fun getNotes(): List<Note>
    suspend fun insertNote(note: Note)
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
}
