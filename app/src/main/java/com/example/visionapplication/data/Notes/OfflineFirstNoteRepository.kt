package com.example.visionapplication.data.Notes

import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.model.Note
import com.example.visionapplication.network.NoteApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Offline first note repository
 *
 * @property noteDao
 * @property noteApi
 * @constructor Create empty Offline first note repository
 */
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
        noteApi.createNote(GlobalDoctor.authedDoctor!!.bearerToken, note)
    }

    override suspend fun updateNote(note: Note) {
        note.asDbNote().let { noteDao.update(it) }
        noteApi.updateNote(GlobalDoctor.authedDoctor!!.bearerToken, note)
    }

    override suspend fun deleteNote(note : Note) {
        note.asDbNote().let { noteDao.delete(it) }
        noteApi.deleteNote(GlobalDoctor.authedDoctor!!.bearerToken, note)
    }

    override suspend fun refreshNotes() {
        noteApi.getNotes(GlobalDoctor.authedDoctor!!.bearerToken)
            .also { externalNotes -> noteDao.deleteAndInsert(notes = externalNotes.map(Note::asDbNote)) }
    }
}