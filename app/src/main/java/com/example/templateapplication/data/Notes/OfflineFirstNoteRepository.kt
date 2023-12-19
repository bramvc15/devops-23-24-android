package com.example.templateapplication.data.Notes

import com.example.templateapplication.model.Note
import com.example.templateapplication.network.NoteApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OfflineFirstNoteRepository(private val noteDao: NoteDao, private val noteApi: NoteApiService) : NoteRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            updateNotesInBackground()
        }
    }

    private suspend fun updateNotesInBackground() {
        while (true) {
            refreshNotes()
            delay(300000)
        }
    }

    override fun getAllNotesStream(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { notes -> notes.map { it.asDomainNote() } }
            .onEach {
                if (it.isEmpty()) {
                refreshNotes()
                }
            }
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insert(note.asDbNote())
        noteApi.createNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.update(note.asDbNote())
        noteApi.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(note.asDbNote())
        noteApi.deleteNote(note)
    }

    override suspend fun refreshNotes() {
        noteApi.getNotes()
            .also { externalNotes -> noteDao.deleteAndInsert(notes = externalNotes.map(Note::asDbNote)) }
    }
}