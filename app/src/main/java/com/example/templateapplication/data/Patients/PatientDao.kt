package com.example.templateapplication.data.Patients

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(patient: dbPatient)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPatients(patients: List<dbPatient>): List<Long>

    @Query("SELECT * from patients")
    fun getAllPatients(): Flow<List<dbPatient>>

    @Query("SELECT * from patients WHERE patient_Id = :patientId")
    fun getPatient(patientId: Int): Flow<dbPatient>

    @Query("DELETE from patients")
    suspend fun deleteAllPatients()

    @Transaction
    suspend fun deleteAndInsert(patients: List<dbPatient>) {
        deleteAllPatients()
        insertPatients(patients)
    }
}