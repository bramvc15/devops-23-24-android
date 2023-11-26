package com.example.templateapplication.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.templateapplication.MainActivity
import com.example.templateapplication.data.DoctorsRepository


class DoctorViewModel(private val doctorsRepository: DoctorsRepository) : ViewModel()  {

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainActivity)
                val doctorsRepository = application.container.doctorsRepository
                DoctorViewModel(doctorsRepository = doctorsRepository)
            }
        }
    }


}

