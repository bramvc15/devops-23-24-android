package com.example.templateapplication.ui.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.templateapplication.MyApplication
import com.example.templateapplication.data.Notes.NoteRepository
import com.example.templateapplication.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NoteUiState {
    data class Success(val notes: List<Note>) : NoteUiState
    data class Error(val errorMessage: String) : NoteUiState
    object Loading : NoteUiState
}

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    var noteUiState: NoteUiState by mutableStateOf(NoteUiState.Loading)
        private set

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() = _notes

    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            noteUiState = NoteUiState.Loading
            noteUiState = try {
                val notesFlow = noteRepository.getAllNotesStream()

                notesFlow.collect { note ->
                    _notes.value = note
                }

                NoteUiState.Success(notes = notes.value)
            } catch (e: IOException) {
                Log.d("NoteViewModel", "IOException")
                Log.d("NoteViewModel", e.message.toString())
                Log.d("NoteViewModel", e.stackTraceToString())
                NoteUiState.Error("IOException error: ${e.message}")
            } catch (e: HttpException) {
                Log.d("NoteViewModel", "HttpException")
                Log.d("NoteViewModel", e.message.toString())
                Log.d("NoteViewModel", e.stackTraceToString())
                NoteUiState.Error("HttpException error: ${e.message}")
            } catch (e: Exception) {
                Log.d("NoteViewModel", "Exception")
                Log.d("NoteViewModel", e.message.toString())
                Log.d("NoteViewModel", e.stackTraceToString())
                NoteUiState.Error("Exception error: ${e.message}")
            }
        }
    }

    fun insertNote(note : Note){
        viewModelScope.launch {
            try {
                noteRepository.insertNote(note)
            } catch (e: Exception) {
                Log.d("NoteViewModel", "Exception during insertNote")
                Log.d("NoteViewModel", e.message.toString())
                Log.d("NoteViewModel", e.stackTraceToString())
                noteUiState = NoteUiState.Error("Exception error: ${e.message}")
            }
        }
    }

    fun deleteNote(note : Note) {
        viewModelScope.launch {
            try {
                Log.d("hallo", "ik ben hier")
                noteRepository.deleteNote(note)
            } catch (e: Exception) {
                Log.d("delete", "Exception during insertNote")
                Log.d("delete", e.message.toString())
                Log.d("delete", e.stackTraceToString())
                noteUiState = NoteUiState.Error("Exception error: ${e.message}")
            }
        }
    }

    /**
     * Factory for [NoteViewModel] that takes [NoteRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val noteRepository = application.container.noteRepository
                NoteViewModel(noteRepository = noteRepository)
            }
        }
    }
}