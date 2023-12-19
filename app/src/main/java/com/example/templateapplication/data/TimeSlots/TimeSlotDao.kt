package com.example.templateapplication.data.TimeSlots

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.templateapplication.data.TimeSlots.dbTimeSlot
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeSlotDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timeSlot: dbTimeSlot)

    @Update
    suspend fun update(timeSlot: dbTimeSlot)

    @Delete
    suspend fun delete(timeSlot: dbTimeSlot)

    @Query("SELECT * from timeslots")
    fun getAllTimeSlots(): Flow<List<dbTimeSlot>>

    @Query("SELECT * from timeslots WHERE id = :id")
    fun getTimeSlot(id: Int): Flow<dbTimeSlot>
}