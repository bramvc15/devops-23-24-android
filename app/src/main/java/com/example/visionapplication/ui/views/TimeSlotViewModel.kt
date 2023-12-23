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
import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.data.TimeSlots.TimeSlotRepository
import com.example.visionapplication.model.Doctor
import com.example.visionapplication.model.TimeSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface TimeSlotUiState {
    data class Success(val timeslots: List<TimeSlot>) : TimeSlotUiState
    data class Error(val errorMessage: String) : TimeSlotUiState
    object Loading : TimeSlotUiState
}

class TimeSlotViewModel(private val timeSlotRepository: TimeSlotRepository) : ViewModel() {
    var timeSlotUiState: TimeSlotUiState by mutableStateOf(TimeSlotUiState.Loading)
        private set

    private val _timeslots = MutableStateFlow<List<TimeSlot>>(emptyList())
    val timeslots: StateFlow<List<TimeSlot>> get() = _timeslots

    init {
        fun getSelectedDoctor(): Doctor? {
            return GlobalDoctor.doctor
        }
        getTimeSlots(getSelectedDoctor())
    }

    private fun getTimeSlots(doctor: Doctor?) {
        viewModelScope.launch {
            timeSlotUiState = TimeSlotUiState.Loading
            timeSlotUiState = try {

                doctor?.let { timeSlotRepository.getTimeSlotsStream(it.id) }?.collect { ts ->
                    _timeslots.value = ts
                }

                TimeSlotUiState.Success(timeslots = timeslots.value)
            } catch (e: IOException) {
                Log.d("TimeSlotViewModel", "IOException")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
                TimeSlotUiState.Error("IOException error: ${e.message}")
            } catch (e: HttpException) {
                Log.d("TimeSlotViewModel", "HttpException")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
                TimeSlotUiState.Error("HttpException error: ${e.message}")
            } catch (e: Exception) {
                Log.d("TimeSlotViewModel", "Exception")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
                TimeSlotUiState.Error("Exception error: ${e.message}")
            }
        }
    }

    fun updateTimeSlot(timeSlot: TimeSlot) {
        viewModelScope.launch {
            try {
                timeSlotRepository.updateTimeSlot(timeSlot)
            } catch (e: IOException) {
                Log.d("TimeSlotViewModel", "IOException")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
            } catch (e: HttpException) {
                Log.d("TimeSlotViewModel", "HttpException")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("TimeSlotViewModel", "Exception")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
            }
        }
    }

    fun selectDoctor(doctor: Doctor) {
        GlobalDoctor.doctor = doctor
        getTimeSlots(doctor)
    }

    /**
     * Factory for [TimeSlotViewModel] that takes [TimeSlotRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val timeSlotRepository = application.container.timeSlotRepository
                TimeSlotViewModel(timeSlotRepository = timeSlotRepository)
            }
        }
    }
}


