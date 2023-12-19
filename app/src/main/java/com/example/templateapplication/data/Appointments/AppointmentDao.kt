package com.example.templateapplication.data.Appointments

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(appointment: dbAppointment)

    @Update
    suspend fun update(appointment: dbAppointment)

    @Delete
    suspend fun delete(appointment: dbAppointment)

    @Query("SELECT * from appointments")
    fun getAllAppointments(): Flow<List<dbAppointment>>

    @Query("SELECT * from appointments WHERE appointment_Id = :id")
    fun getAppointment(id: Int): Flow<dbAppointment>
}