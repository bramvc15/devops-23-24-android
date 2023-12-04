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


    data class Success(val doctors: List<Doctor>) : DoctorUiState
    object Error : DoctorUiState
    object Loading : DoctorUiState
}


class DoctorViewModel() : ViewModel()  {

    var doctorUiState : DoctorUiState  by mutableStateOf(DoctorUiState.Loading)
        private set

    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> get() = _doctors

    var selectedDoctor: Doctor? = null

    private fun setDoctors(newDoctors: List<Doctor>) {
        _doctors.value = newDoctors
    }

    init {
        getDoctors()
    }


    fun getDoctors() {
        viewModelScope.launch {
            doctorUiState = DoctorUiState.Loading
            try {
                val listResult = DoctorApi.retrofitService.getDoctors()
                setDoctors(listResult)
                doctorUiState = DoctorUiState.Success(listResult)
            } catch (e: IOException) {
                doctorUiState = DoctorUiState.Error
            } catch (e: HttpException) {
                doctorUiState = DoctorUiState.Error
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



