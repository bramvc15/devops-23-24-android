package com.example.templateapplication.data.Patients

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(patient: dbPatient)

    @Update
    suspend fun update(patient: dbPatient)

    @Delete
    suspend fun delete(patient: dbPatient)

    @Query("SELECT * from patients")
    fun getAllPatients(): Flow<List<dbPatient>>

    @Query("SELECT * from patients WHERE patient_id = :id")
    fun getPatient(id: Int): Flow<dbPatient>
}