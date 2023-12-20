package com.example.templateapplication.ui.screens.notes.components

import com.example.templateapplication.model.Note
import com.example.templateapplication.ui.screens.notes.util.NoteOrder
import com.example.templateapplication.ui.screens.notes.util.OrderType


data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)