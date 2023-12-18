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
import com.example.templateapplication.data.PatientRepository
import com.example.templateapplication.model.Patient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface PatientUiState {
    data class Success(val patients: List<Patient>) : PatientUiState
    data class Error(val errorMessage: String) : PatientUiState
    object Loading : PatientUiState
}

class PatientViewModel(private val patientRepository: PatientRepository) : ViewModel() {
    var patientUiState: PatientUiState by mutableStateOf(PatientUiState.Loading)
        private set

    private val _patients = MutableStateFlow<List<Patient>>(emptyList())
    val patients: StateFlow<List<Patient>> get() = _patients

    init {
        getPatients()
    }

    private fun getPatients() {
        viewModelScope.launch {
            patientUiState = PatientUiState.Loading
            patientUiState = try {
                val patients = patientRepository.getPatients()
                _patients.value = patients
                PatientUiState.Success(patients)
            } catch (e: IOException) {
                Log.d("TimeSlotViewModel", "IOException")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
                PatientUiState.Error("IOException error: ${e.message}")
            } catch (e: HttpException) {
                Log.d("TimeSlotViewModel", "HttpException")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
                PatientUiState.Error("HttpException error: ${e.message}")
            } catch (e: Exception) {
                Log.d("TimeSlotViewModel", "Exception")
                Log.d("TimeSlotViewModel", e.message.toString())
                Log.d("TimeSlotViewModel", e.stackTraceToString())
                PatientUiState.Error("Exception error: ${e.message}")
            }
        }
    }
    /**
     * Factory for [PatientViewModel] that takes [PatientRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val patientRepository = application.container.patientRepository
                PatientViewModel(patientRepository = patientRepository)
            }
        }
    }
}


