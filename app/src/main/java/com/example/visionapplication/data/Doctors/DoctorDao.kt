package com.example.visionapplication.data.Doctors

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Doctor dao
 *
 * @constructor Create empty Doctor dao
 */
@Dao
interface DoctorDao {
    /**
     * Insert
     *
     * @param doctor
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(doctor: dbDoctor)

    /**
     * Insert doctors
     *
     * @param doctors
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDoctors(doctors: List<dbDoctor>): List<Long>

    /**
     * Get all doctors
     *
     * @return
     */
    @Query("SELECT * from doctors")
    fun getAllDoctors(): Flow<List<dbDoctor>>

    /**
     * Get doctor
     *
     * @param id
     * @return
     */
    @Query("SELECT * from doctors WHERE id = :id")
    fun getDoctor(id: Int): Flow<dbDoctor?>

    /**
     * Delete all doctors
     *
     */
    @Query("DELETE from doctors")
    suspend fun deleteAllDoctors()

    /**
     * Delete and insert
     *
     * @param doctors
     */
    @Transaction
    suspend fun deleteAndInsert(doctors: List<dbDoctor>) {
        deleteAllDoctors()
        insertDoctors(doctors)
    }
}