package com.example.templateapplication.ui.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.PatientDTO
import com.example.templateapplication.network.AppointmentApi
import com.example.templateapplication.network.DoctorApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface AppointmentUiState {
    data class Success(val appointments: List<Appointment>) : AppointmentUiState
    object Error : AppointmentUiState
    object Loading : AppointmentUiState
}


class AppointmentViewModel() : ViewModel()  {

    var appointmentUiState : AppointmentUiState  by mutableStateOf(AppointmentUiState.Loading)
        private set

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> get() = _appointments

    private fun setAppointments(newAppointments: List<Appointment>) {
        _appointments.value = newAppointments
    }

    init {
        //getAppointments()
    }


    fun getAppointments(patient: PatientDTO) {
        viewModelScope.launch {
            appointmentUiState = AppointmentUiState.Loading
            try {
                val listResult = AppointmentApi.retrofitService.getAppointments(patient)
                setAppointments(listResult)
                appointmentUiState = AppointmentUiState.Success(listResult)
            } catch (e: IOException) {
                appointmentUiState = AppointmentUiState.Error
            } catch (e: HttpException) {
                appointmentUiState = AppointmentUiState.Error
            }

        }
    }

    /*
    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as MyApplication
                val doctorsRepository = application.container.doctorsRepository
                DoctorViewModel(doctorsRepository = doctorsRepository)
            }
        }
    }*/
}



