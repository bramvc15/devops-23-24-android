package com.example.visionapplication.data.Notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: dbNote)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notes: List<dbNote>): List<Long>

    @Update
    suspend fun update(note: dbNote)

    @Delete
    suspend fun delete(note: dbNote)

    @Query("SELECT * from notes")
    fun getAllNotes(): Flow<List<dbNote>>

    @Query("SELECT * from notes WHERE id = :id")
    fun getNote(id: Int): Flow<dbNote>

    @Query("DELETE from notes")
    fun deleteAllNotes()

    @Transaction
    suspend fun deleteAndInsert(notes: List<dbNote>) {
        deleteAllNotes()
        insertNotes(notes)
    }
}