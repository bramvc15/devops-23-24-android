package com.example.templateapplication.data

import android.content.Context
import com.example.templateapplication.network.AppointmentApiService
import com.example.templateapplication.network.DoctorApiService
import com.example.templateapplication.network.NoteApiService
import com.example.templateapplication.network.PatientApiService
import com.example.templateapplication.network.TimeSlotApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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
    private val baseUrl = "http://192.168.0.227:5001/api/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * DI implementation for Doctor repository
     */
    override val doctorRepository: DoctorRepository by lazy {
        NetworkDoctorRepository(
            VisionDatabase.getDatabase(context = context).doctorDao(),
            retrofit.create(DoctorApiService::class.java)
        )
    }

    /**
     * DI implementation for Appointment repository
     */
    override val appointmentRepository: AppointmentRepository by lazy {
        NetworkAppointmentRepository(
            //VisionDatabase.getDatabase(context = context).appointmentDao(),
            retrofit.create(AppointmentApiService::class.java)
        )
    }

    /**
     * DI implementation for TimeSlot repository
     */
    override val timeSlotRepository: TimeSlotRepository by lazy {
        NetworkTimeSlotRepository(
            //VisionDatabase.getDatabase(context = context).appointmentDao(),
            retrofit.create(TimeSlotApiService::class.java)
        )
    }

    /**
     * DI implementation for Patient repository
     */
    override val patientRepository: PatientRepository by lazy {
        NetworkPatientRepository(
            //VisionDatabase.getDatabase(context = context).appointmentDao(),
            retrofit.create(PatientApiService::class.java)
        )
    }

    /**
     * DI implementation for Note repository
     */
    override val noteRepository: NoteRepository by lazy {
        NetworkNoteRepository(
            VisionDatabase.getDatabase(context = context).noteDao(),
            retrofit.create(NoteApiService::class.java)
        )
    }
}
