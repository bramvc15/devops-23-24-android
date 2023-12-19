package com.example.templateapplication.data.TimeSlots

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeSlotDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timeSlot: dbTimeSlot)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTimeSlots(timeSlots: List<dbTimeSlot?>): List<Long>

    @Update
    suspend fun update(timeSlot: dbTimeSlot)

    @Delete
    suspend fun delete(timeSlot: dbTimeSlot)

    @Query("SELECT * from timeslots WHERE doctorId = :doctorId")
    fun getAllTimeSlots(doctorId: Int): Flow<List<dbTimeSlot>>

    @Query("SELECT * from timeslots WHERE timeslotId = :id")
    fun getTimeSlot(id: Int): Flow<dbTimeSlot>

    @Query("DELETE from timeslots")
    fun deleteAllTimeSlots()

    @Transaction
    suspend fun deleteAndInsert(timeSlots: kotlin.collections.List<com.example.templateapplication.data.TimeSlots.dbTimeSlot?>) {
        deleteAllTimeSlots()
        insertTimeSlots(timeSlots)
    }
}