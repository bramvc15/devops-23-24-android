package com.example.templateapplication.data.Doctors

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.templateapplication.data.Doctors.dbDoctor
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(doctor: dbDoctor)

    @Query("SELECT * from doctors")
    fun getAllDoctors(): Flow<List<dbDoctor>>

    @Query("SELECT * from doctors WHERE id = :id")
    fun getDoctor(id: Int): Flow<dbDoctor>
}