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
import com.example.visionapplication.data.Patients.PatientRepository
import com.example.visionapplication.model.Patient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


/**
 * Patient ui state
 *
 * @constructor Create empty Patient ui state
 */
sealed interface PatientUiState {
    /**
     * Success
     *
     * @property patients
     * @constructor Create empty Success
     */
    data class Success(val patients: List<Patient>) : PatientUiState

    /**
     * Error
     *
     * @property errorMessage
     * @constructor Create empty Error
     */
    data class Error(val errorMessage: String) : PatientUiState
    object Loading : PatientUiState
}

/**
 * Patient view model
 *
 * @property patientRepository
 * @constructor Create empty Patient view model
 */
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
                val patientsFlow = patientRepository.getPatientsStream()

                val patients: MutableList<Patient> = mutableListOf()
                patientsFlow.collect { pa ->
                    patients.addAll(pa)
                }

                _patients.value = patients
                PatientUiState.Success(patients)
            } catch (e: IOException) {
                Log.d("PatientViewModel", "IOException")
                Log.d("PatientViewModel", e.message.toString())
                Log.d("PatientViewModel", e.stackTraceToString())
                PatientUiState.Error("IOException error: ${e.message}")
            } catch (e: HttpException) {
                Log.d("PatientViewModel", "HttpException")
                Log.d("PatientViewModel", e.message.toString())
                Log.d("PatientViewModel", e.stackTraceToString())
                PatientUiState.Error("HttpException error: ${e.message}")
            } catch (e: Exception) {
                Log.d("PatientViewModel", "Exception")
                Log.d("PatientViewModel", e.message.toString())
                Log.d("PatientViewModel", e.stackTraceToString())
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


