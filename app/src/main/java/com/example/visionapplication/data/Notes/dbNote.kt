package com.example.visionapplication.data.Notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.visionapplication.model.Note

@Entity(tableName = "notes")
data class dbNote(
    @PrimaryKey
    val id: Int?,
    val title: String,
    val content: String
)

fun dbNote.asDomainNote(): Note {
    return Note(id = this.id,
        title = this.title,
        content = this.content)
}

fun Note.asDbNote(): dbNote {
    return dbNote(
        id = this.id,
        title = this.title,
        content = this.content
    )
}

fun List<dbNote>.asDomainNotes(): List<Note> {
    var noteList = this.map {
        Note(
            it.id,
            it.title,
            it.content
        )
    }
    return noteList
}
