package com.example.visionapplication.data.Notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Note dao
 *
 * @constructor Create empty Note dao
 */
@Dao
interface NoteDao {
    /**
     * Insert
     *
     * @param note
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: dbNote)

    /**
     * Insert notes
     *
     * @param notes
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notes: List<dbNote>): List<Long>

    /**
     * Update
     *
     * @param note
     */
    @Update
    suspend fun update(note: dbNote)

    /**
     * Delete
     *
     * @param note
     */
    @Delete
    suspend fun delete(note: dbNote)

    /**
     * Get all notes
     *
     * @return
     */
    @Query("SELECT * from notes")
    fun getAllNotes(): Flow<List<dbNote>>

    /**
     * Get note
     *
     * @param id
     * @return
     */
    @Query("SELECT * from notes WHERE id = :id")
    fun getNote(id: Int): Flow<dbNote>

    /**
     * Delete all notes
     *
     */
    @Query("DELETE from notes")
    fun deleteAllNotes()

    /**
     * Delete and insert
     *
     * @param notes
     */
    @Transaction
    suspend fun deleteAndInsert(notes: List<dbNote>) {
        deleteAllNotes()
        insertNotes(notes)
    }
}