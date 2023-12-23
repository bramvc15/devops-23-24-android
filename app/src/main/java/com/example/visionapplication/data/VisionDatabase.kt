package com.example.visionapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.visionapplication.data.Appointments.AppointmentDao
import com.example.visionapplication.data.Appointments.dbAppointment
import com.example.visionapplication.data.Doctors.DoctorDao
import com.example.visionapplication.data.Doctors.dbDoctor
import com.example.visionapplication.data.Notes.NoteDao
import com.example.visionapplication.data.Notes.dbNote
import com.example.visionapplication.data.Patients.PatientDao
import com.example.visionapplication.data.Patients.dbPatient
import com.example.visionapplication.data.TimeSlots.TimeSlotDao
import com.example.visionapplication.data.TimeSlots.dbTimeSlot

@Database(entities = [dbDoctor::class, dbNote::class, dbAppointment::class, dbPatient::class, dbTimeSlot::class], version = 5, exportSchema = false)
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