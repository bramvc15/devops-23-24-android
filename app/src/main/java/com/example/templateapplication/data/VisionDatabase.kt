package com.example.templateapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [dbDoctor::class], version = 1, exportSchema = false)
abstract class VisionDatabase : RoomDatabase() {
    abstract fun doctorDao(): DoctorDao
    abstract fun noteDao(): NoteDao
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