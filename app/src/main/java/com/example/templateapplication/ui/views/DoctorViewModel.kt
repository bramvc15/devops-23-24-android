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
import com.example.templateapplication.data.Doctors.DoctorRepository
import com.example.templateapplication.data.GlobalDoctor
import com.example.templateapplication.model.Doctor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface DoctorUiState {
    data class Success(val doctors: List<Doctor>) : DoctorUiState
    data class Error(val errorMessage: String) : DoctorUiState
    object Loading : DoctorUiState
}

class DoctorViewModel(private val doctorRepository: DoctorRepository) : ViewModel()  {
    var doctorUiState: DoctorUiState by mutableStateOf(DoctorUiState.Loading)
        private set

    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> get() = _doctors

    init {
        getDoctors()
    }

    private fun getDoctors() {
        viewModelScope.launch {
            doctorUiState = DoctorUiState.Loading
            doctorUiState = try {
                val doctors = doctorRepository.getAllDoctorsStream()
                _doctors.value = doctors
                DoctorUiState.Success(doctors)
            } catch (e: IOException) {
                Log.d("DoctorViewModel", "IOException")
                Log.d("DoctorViewModel", e.message.toString())
                Log.d("DoctorViewModel", e.stackTraceToString())
                DoctorUiState.Error("IOException error: ${e.message}")
            } catch (e: HttpException) {
                Log.d("DoctorViewModel", "HttpException")
                Log.d("DoctorViewModel", e.message.toString())
                Log.d("DoctorViewModel", e.stackTraceToString())
                DoctorUiState.Error("HttpException error: ${e.message}")
            } catch (e: Exception) {
                Log.d("DoctorViewModel", "Exception")
                Log.d("DoctorViewModel", e.message.toString())
                Log.d("DoctorViewModel", e.stackTraceToString())
                DoctorUiState.Error("Exception error: ${e.message}")
            }
        }
    }

    // Niet zeker of dit oke is
    fun selectDoctor(doctor: Doctor) {
        GlobalDoctor.doctor = doctor
    }

    /**
     * Factory for [DoctorViewModel] that takes [DoctorRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val doctorRepository = application.container.doctorRepository
                DoctorViewModel(doctorRepository = doctorRepository)
            }
        }
    }
}



