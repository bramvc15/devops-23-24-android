package com.example.templateapplication.ui.views

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
import com.example.templateapplication.MyApplication
import com.example.templateapplication.data.AppointmentRepository
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.PatientDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface AppointmentUiState {
    data class Success(val appointments: List<Appointment>) : AppointmentUiState
    data class Error(val errorMessage: String) : AppointmentUiState
    object Loading : AppointmentUiState
}

class AppointmentViewModel(private val appointmentRepository: AppointmentRepository) : ViewModel()  {
    var appointmentUiState: AppointmentUiState by mutableStateOf(AppointmentUiState.Loading)
        private set

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> get() = _appointments

    init {
        //getAppointments()
    }


    fun getAppointments(patient: PatientDTO) {
        viewModelScope.launch {
            appointmentUiState = AppointmentUiState.Loading
            appointmentUiState = try {
                val appointments = appointmentRepository.getAppointments(patient)
                _appointments.value = appointments
                AppointmentUiState.Success(appointments)
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



