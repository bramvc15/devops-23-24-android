package com.example.templateapplication.data.Doctors

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.templateapplication.data.Doctors.dbDoctor
import com.example.templateapplication.model.Doctor
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(doctor: dbDoctor)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDoctors(doctors: List<dbDoctor>): List<Long>

    @Query("SELECT * from doctors")
    fun getAllDoctors(): Flow<List<dbDoctor>>

    @Query("SELECT * from doctors WHERE id = :id")
    fun getDoctor(id: Int): Flow<dbDoctor?>

    @Query("DELETE FROM doctors")
    suspend fun deleteAllDoctors()

    @Transaction
    suspend fun deleteAndInsert(doctors: List<dbDoctor>) {
        deleteAllDoctors()
        insertDoctors(doctors)
    }
}