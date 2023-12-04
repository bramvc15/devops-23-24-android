package com.example.templateapplication.ui.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.templateapplication.model.Appointment
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.model.PatientDTO
import com.example.templateapplication.model.TimeSlot
import com.example.templateapplication.network.AppointmentApi
import com.example.templateapplication.network.TimeSlotApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface TimeSlotUiState {
    data class Success(val timeslots: List<TimeSlot>) : TimeSlotUiState
    object Error : TimeSlotUiState
    object Loading : TimeSlotUiState
}


class TimeSlotViewModel(doctorViewModel: DoctorViewModel) : ViewModel()  {

    var timeSlotUiState : TimeSlotUiState  by mutableStateOf(TimeSlotUiState.Loading)
        private set

    private val _timeslots = MutableStateFlow<List<TimeSlot>>(emptyList())
    val timeslots: StateFlow<List<TimeSlot>> get() = _timeslots

    private fun setTimeSlots(newTimeSlots: List<TimeSlot>) {
        _timeslots.value = newTimeSlots
    }

/*    init {
        Log.d("TimeSlotViewModel", "doctorViewModel.selectedDoctor: ${doctorViewModel.selectedDoctor}")
        getTimeSlots(doctorViewModel.selectedDoctor!!)
    }*/


    fun getTimeSlots(doctor: Doctor) {
        viewModelScope.launch {
            timeSlotUiState = TimeSlotUiState.Loading
            try {
                val listResult = TimeSlotApi.retrofitService.getTimeSlots(id = doctor.id)
                Log.d("TimeSlotViewModel", "listResult: $listResult")
                setTimeSlots(listResult)
                timeSlotUiState = TimeSlotUiState.Success(listResult)
            } catch (e: IOException) {
                timeSlotUiState = TimeSlotUiState.Error
            } catch (e: HttpException) {
                timeSlotUiState = TimeSlotUiState.Error
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



