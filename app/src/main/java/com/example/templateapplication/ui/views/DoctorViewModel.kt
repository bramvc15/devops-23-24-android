package com.example.templateapplication.ui.views

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.templateapplication.model.Doctor
import com.example.templateapplication.network.DoctorApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface DoctorUiState {
    data class Success(val doctors: List<Doctor>, val selectedDoctor: Doctor?) : DoctorUiState
    data class Error(val errorMessage: String) : DoctorUiState
    object Loading : DoctorUiState
}



class DoctorViewModel() : ViewModel()  {


    private var doctorUiState: DoctorUiState by mutableStateOf(DoctorUiState.Loading)

    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> get() = _doctors

    var selectedDoctor: Doctor? by mutableStateOf(null)

    private fun setDoctors(newDoctors: List<Doctor>) {
        _doctors.value = newDoctors
    }

    fun selectDoctor(doctor: Doctor) {
        selectedDoctor = doctor
    }

    init {
        getDoctors()
    }

    private fun getDoctors() {
        viewModelScope.launch {
            doctorUiState = DoctorUiState.Loading
            doctorUiState = try {
                val listResult = DoctorApi.retrofitService.getDoctors()
                setDoctors(listResult)
                DoctorUiState.Success(listResult, selectedDoctor)
            } catch (e: IOException) {
                Log.d("here", "here")
                DoctorUiState.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                DoctorUiState.Error("HTTP error: ${e.message}")
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



