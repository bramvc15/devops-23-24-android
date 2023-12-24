package com.example.visionapplication.data

import android.content.Context
import com.example.visionapplication.data.Appointments.AppointmentRepository
import com.example.visionapplication.data.Appointments.OfflineFirstAppointmentRepository
import com.example.visionapplication.data.Doctors.DoctorRepository
import com.example.visionapplication.data.Doctors.OfflineFirstDoctorRepository
import com.example.visionapplication.data.Notes.NoteRepository
import com.example.visionapplication.data.Notes.OfflineFirstNoteRepository
import com.example.visionapplication.data.Patients.OfflineFirstPatientRepository
import com.example.visionapplication.data.Patients.PatientRepository
import com.example.visionapplication.data.TimeSlots.OfflineFirstTimeSlotRepository
import com.example.visionapplication.data.TimeSlots.TimeSlotRepository
import com.example.visionapplication.network.AppointmentApiService
import com.example.visionapplication.network.DoctorApiService
import com.example.visionapplication.network.NoteApiService
import com.example.visionapplication.network.PatientApiService
import com.example.visionapplication.network.TimeSlotApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * App container
 *
 * @constructor Create empty App container
 */
interface AppContainer {
    val doctorRepository: DoctorRepository
    val appointmentRepository: AppointmentRepository
    val timeSlotRepository: TimeSlotRepository
    val patientRepository: PatientRepository
    val noteRepository: NoteRepository
}

/**
 * Default app container
 *
 * @property context
 * @constructor Create empty Default app container
 */
class DefaultAppContainer(private val context: Context): AppContainer {
    private val baseUrl = "https://vichogent.be:40157/api/"

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
