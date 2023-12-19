package com.example.templateapplication.data.Appointments

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(appointment: dbAppointment)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppointments(appointments: List<dbAppointment>): List<Long>

    @Update
    suspend fun update(appointment: dbAppointment)

    @Delete
    suspend fun delete(appointment: dbAppointment)

    @Query("SELECT * from appointments")
    fun getAllAppointments(): Flow<List<dbAppointment>>

    @Query("SELECT * from appointments WHERE appointment_id = :id")
    fun getAppointment(id: Int): Flow<dbAppointment>

    @Query("DELETE from appointments")
    fun deleteAllAppointments()

    @Transaction
    suspend fun deleteAndInsert(appointments: List<dbAppointment>) {
        deleteAllAppointments()
        insertAppointments(appointments)
    }
}