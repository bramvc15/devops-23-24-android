package com.example.visionapplication.ui.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.visionapplication.MyApplication
import com.example.visionapplication.data.Appointments.AppointmentRepository
import com.example.visionapplication.model.Appointment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


/**
 * Appointment ui state
 *
 * @constructor Create empty Appointment ui state
 */
sealed interface AppointmentUiState {
    /**
     * Success
     *
     * @property appointments
     * @constructor Create empty Success
     */
    data class Success(val appointments: List<Appointment>) : AppointmentUiState

    /**
     * Error
     *
     * @property errorMessage
     * @constructor Create empty Error
     */
    data class Error(val errorMessage: String) : AppointmentUiState
    object Loading : AppointmentUiState
}

/**
 * Appointment view model
 *
 * @property appointmentRepository
 * @constructor Create empty Appointment view model
 */
class AppointmentViewModel(private val appointmentRepository: AppointmentRepository) : ViewModel()  {
    var appointmentUiState: AppointmentUiState by mutableStateOf(AppointmentUiState.Loading)
        private set

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> get() = _appointments

    init {
        getAppointments()
    }


    /**
     * Get appointments
     *
     */
    fun getAppointments() {
        viewModelScope.launch {
            appointmentUiState = AppointmentUiState.Loading
            appointmentUiState = try {
                val appointmentsFlow = appointmentRepository.getAppointments()

                appointmentsFlow.collect { app ->
                    _appointments.value = app
                }

                AppointmentUiState.Success(appointments = appointments.value)

            } catch (e: IOException) {
                Log.d("AppointmentViewModel", "IOException")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
                AppointmentUiState.Error("IOException error: ${e.message}")
            } catch (e: HttpException) {
                Log.d("AppointmentViewModel", "HttpException")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
                AppointmentUiState.Error("HttpException error: ${e.message}")
            } catch (e: Exception) {
                Log.d("AppointmentViewModel", "Exception")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
                AppointmentUiState.Error("Exception error: ${e.message}")
            }
        }
    }

    /**
     * Update appointment
     *
     * @param appointment
     */
    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                appointmentRepository.updateAppointment(appointment)
            } catch (e: IOException) {
                Log.d("AppointmentViewModel", "IOException")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
            } catch (e: HttpException) {
                Log.d("AppointmentViewModel", "HttpException")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("AppointmentViewModel", "Exception")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
            }
        }
    }

    /**
     * Delete appointment
     *
     * @param appointment
     */
    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                appointmentRepository.deleteAppointment(appointment)
                getAppointments()
            } catch (e: IOException) {
                Log.d("AppointmentViewModel", "IOException")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
            } catch (e: HttpException) {
                Log.d("AppointmentViewModel", "HttpException")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("AppointmentViewModel", "Exception")
                Log.d("AppointmentViewModel", e.message.toString())
                Log.d("AppointmentViewModel", e.stackTraceToString())
            }
        }
    }

    /**
     * Factory for [AppointmentViewModel] that takes [AppointmentRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val appointmentRepository = application.container.appointmentRepository
                AppointmentViewModel(appointmentRepository = appointmentRepository)
            }
        }
    }
}



