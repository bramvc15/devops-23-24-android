package com.example.visionapplication.data.TimeSlots

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Time slot dao
 *
 * @constructor Create empty Time slot dao
 */
@Dao
interface TimeSlotDao {
    /**
     * Insert
     *
     * @param timeSlot
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timeSlot: dbTimeSlot)

    /**
     * Insert time slots
     *
     * @param timeSlots
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTimeSlots(timeSlots: List<dbTimeSlot?>): List<Long>

    /**
     * Update
     *
     * @param timeSlot
     */
    @Update
    suspend fun update(timeSlot: dbTimeSlot)

    /**
     * Delete
     *
     * @param timeSlot
     */
    @Delete
    suspend fun delete(timeSlot: dbTimeSlot)

    /**
     * Get all time slots
     *
     * @param doctorId
     * @return
     */
    @Query("SELECT * from timeslots WHERE doctorId = :doctorId")
    fun getAllTimeSlots(doctorId: Int): Flow<List<dbTimeSlot>>

    /**
     * Get time slot
     *
     * @param id
     * @return
     */
    @Query("SELECT * from timeslots WHERE timeslotId = :id")
    fun getTimeSlot(id: Int): Flow<dbTimeSlot>

    /**
     * Delete all time slots
     *
     */
    @Query("DELETE from timeslots")
    fun deleteAllTimeSlots()

    /**
     * Delete and insert
     *
     * @param timeSlots
     */
    @Transaction
    suspend fun deleteAndInsert(timeSlots: kotlin.collections.List<com.example.visionapplication.data.TimeSlots.dbTimeSlot?>) {
        deleteAllTimeSlots()
        insertTimeSlots(timeSlots)
    }
}