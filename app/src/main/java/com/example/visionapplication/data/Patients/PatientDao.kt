package com.example.visionapplication.data.Patients

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Patient dao
 *
 * @constructor Create empty Patient dao
 */
@Dao
interface PatientDao {
    /**
     * Insert
     *
     * @param patient
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(patient: dbPatient)

    /**
     * Insert patients
     *
     * @param patients
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPatients(patients: List<dbPatient>): List<Long>

    /**
     * Get all patients
     *
     * @return
     */
    @Query("SELECT * from patients")
    fun getAllPatients(): Flow<List<dbPatient>>

    /**
     * Get patient
     *
     * @param patientId
     * @return
     */
    @Query("SELECT * from patients WHERE patient_id = :patientId")
    fun getPatient(patientId: Int): Flow<dbPatient>

    /**
     * Delete all patients
     *
     */
    @Query("DELETE from patients")
    suspend fun deleteAllPatients()

    /**
     * Delete and insert
     *
     * @param patients
     */
    @Transaction
    suspend fun deleteAndInsert(patients: List<dbPatient>) {
        deleteAllPatients()
        insertPatients(patients)
    }
}