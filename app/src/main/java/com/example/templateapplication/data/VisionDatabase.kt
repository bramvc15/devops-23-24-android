package com.example.templateapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.templateapplication.data.Appointments.AppointmentDao
import com.example.templateapplication.data.Doctors.DoctorDao
import com.example.templateapplication.data.Doctors.dbDoctor
import com.example.templateapplication.data.Notes.NoteDao
import com.example.templateapplication.data.Patients.PatientDao
import com.example.templateapplication.data.TimeSlots.TimeSlotDao

@Database(entities = [dbDoctor::class], version = 1, exportSchema = false)
abstract class VisionDatabase : RoomDatabase() {
    abstract fun doctorDao(): DoctorDao
    abstract fun noteDao(): NoteDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun patientDao(): PatientDao
    abstract fun timeSlotDao(): TimeSlotDao
    companion object {
        @Volatile
        private var Instance: VisionDatabase? = null

        fun getDatabase(context: Context): VisionDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, VisionDatabase::class.java, "vision_database").fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}