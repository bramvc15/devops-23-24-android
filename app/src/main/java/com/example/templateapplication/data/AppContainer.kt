package com.example.templateapplication.data

import android.content.Context
import com.example.templateapplication.data.Appointments.AppointmentRepository
import com.example.templateapplication.data.Appointments.OfflineFirstAppointmentRepository
import com.example.templateapplication.data.Doctors.DoctorRepository
import com.example.templateapplication.data.Doctors.OfflineFirstDoctorRepository
import com.example.templateapplication.data.Notes.NoteRepository
import com.example.templateapplication.data.Notes.OfflineFirstNoteRepository
import com.example.templateapplication.data.Patients.OfflineFirstPatientRepository
import com.example.templateapplication.data.Patients.PatientRepository
import com.example.templateapplication.data.TimeSlots.OfflineFirstTimeSlotRepository
import com.example.templateapplication.data.TimeSlots.TimeSlotRepository
import com.example.templateapplication.network.AppointmentApiService
import com.example.templateapplication.network.DoctorApiService
import com.example.templateapplication.network.NoteApiService
import com.example.templateapplication.network.PatientApiService
import com.example.templateapplication.network.TimeSlotApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val doctorRepository: DoctorRepository
    val appointmentRepository: AppointmentRepository
    val timeSlotRepository: TimeSlotRepository
    val patientRepository: PatientRepository
    val noteRepository: NoteRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(private val context: Context): AppContainer {
    private val baseUrl = "http://10.0.2.2:5002/api/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    /**
     * DI implementation for Doctor repository
     */
    override val doctorRepository: DoctorRepository by lazy {
        OfflineFirstDoctorRepository(
            VisionDatabase.getDatabase(context).doctorDao(),
            retrofit.create(DoctorApiService::class.java))
    }

    /**
     * DI implementation for Appointment repository
     */
    override val appointmentRepository: AppointmentRepository by lazy {
        OfflineFirstAppointmentRepository(
            VisionDatabase.getDatabase(context).appointmentDao(),
            retrofit.create(AppointmentApiService::class.java)
        )
    }

    /**
     * DI implementation for TimeSlot repository
     */
    override val timeSlotRepository: TimeSlotRepository by lazy {
        OfflineFirstTimeSlotRepository(
            VisionDatabase.getDatabase(context).timeSlotDao(),
            retrofit.create(TimeSlotApiService::class.java)
        )
    }

    /**
     * DI implementation for Patient repository
     */
    override val patientRepository: PatientRepository by lazy {
        OfflineFirstPatientRepository(
            VisionDatabase.getDatabase(context).patientDao(),
            retrofit.create(PatientApiService::class.java)
        )
    }

    /**
     * DI implementation for Note repository
     */
    override val noteRepository: NoteRepository by lazy {
        OfflineFirstNoteRepository(
            VisionDatabase.getDatabase(context).noteDao(),
            retrofit.create(NoteApiService::class.java)
        )
    }
}
