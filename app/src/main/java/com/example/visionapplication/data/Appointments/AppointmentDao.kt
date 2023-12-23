package com.example.visionapplication.data.Appointments

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Appointment dao
 *
 * @constructor Create empty Appointment dao
 */
@Dao
interface AppointmentDao {
    /**
     * Insert
     *
     * @param appointment
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(appointment: dbAppointment)

    /**
     * Insert appointments
     *
     * @param appointments
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppointments(appointments: List<dbAppointment>): List<Long>

    /**
     * Update
     *
     * @param appointment
     */
    @Update
    suspend fun update(appointment: dbAppointment)

    /**
     * Delete
     *
     * @param appointment
     */
    @Delete
    suspend fun delete(appointment: dbAppointment)

    /**
     * Get all appointments
     *
     * @return
     */
    @Query("SELECT * from appointments")
    fun getAllAppointments(): Flow<List<dbAppointment>>

    /**
     * Get appointment
     *
     * @param id
     * @return
     */
    @Query("SELECT * from appointments WHERE appointment_id = :id")
    fun getAppointment(id: Int): Flow<dbAppointment>

    /**
     * Delete all appointments
     *
     */
    @Query("DELETE from appointments")
    fun deleteAllAppointments()

    /**
     * Delete and insert
     *
     * @param appointments
     */
    @Transaction
    suspend fun deleteAndInsert(appointments: List<dbAppointment>) {
        deleteAllAppointments()
        insertAppointments(appointments)
    }
}