package com.example.visionapplication.ui.views

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.visionapplication.MyApplication
import com.example.visionapplication.data.Doctors.DoctorRepository
import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.model.Doctor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


/**
 * Detail wizard steps
 *
 * @constructor Create empty Detail wizard steps
 */
enum class DetailWizardSteps {
    /**
     * Name
     *
     * @constructor Create empty Name
     */
    NAME,

    /**
     * Success
     *
     * @constructor Create empty Success
     */
    SUCCESS,

    /**
     * Error
     *
     * @constructor Create empty Error
     */
    ERROR,

    /**
     * Loading
     *
     * @constructor Create empty Loading
     */
    LOADING,

    /**
     * Selecteddoctor
     *
     * @constructor Create empty Selecteddoctor
     */
    SELECTEDDOCTOR,
}

/**
 * Doctor ui state
 *
 * @property DoctorStep
 * @property success
 * @property Error
 * @property loading
 * @property selectedDoctor
 * @constructor Create empty Doctor ui state
 */
data class DoctorUiState(
    val DoctorStep: DetailWizardSteps = DetailWizardSteps.NAME,
    val success: List<Doctor>? = null,
    val Error: String? = null,
    val loading: Boolean = false,
    val selectedDoctor: Doctor? = null
)

/**
 * Doctor view model
 *
 * @property doctorRepository
 * @constructor Create empty Doctor view model
 */
class DoctorViewModel(private val doctorRepository: DoctorRepository) : ViewModel()  {
    private val _uiState = MutableStateFlow(VisionUiState())
    val uiState: StateFlow<VisionUiState> = _uiState

    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> get() = _doctors

    init {
        initializeUIState()
    }

    /**
     * Update ui state
     *
     * @param updatedUiState
     */
    fun updateUiState(updatedUiState: VisionUiState) {
        _uiState.value = updatedUiState
    }
    private fun initializeUIState() {
        getDoctors()
        Log.d("DoctorViewModel", "Instance created")
    }

    private fun getDoctors() {
        viewModelScope.launch {
            _uiState.value = VisionUiState.Loading
            try {
                // val token = GlobalDoctor.authedDoctor?.idToken ?: throw Exception("Token is null")
                val doctorsFlow = doctorRepository.getAllDoctorsStream()

                doctorsFlow.collect { doc ->
                    _doctors.value = doc
                }

                _uiState.value = VisionUiState(loading = doctors.value.isEmpty(), success = doctors.value)
            } catch (e: IOException) {
                Log.d("DoctorViewModel", "IOException")
                Log.d("DoctorViewModel", e.message.toString())
                Log.d("DoctorViewModel", e.stackTraceToString())
                _uiState.value = VisionUiState(Error = "IOException error: ${e.message}")
            } catch (e: HttpException) {
                Log.d("DoctorViewModel", "HttpException")
                Log.d("DoctorViewModel", e.message.toString())
                Log.d("DoctorViewModel", e.stackTraceToString())
                _uiState.value = VisionUiState(Error = "HttpException error: ${e.message}")
            } catch (e: Exception) {
                Log.d("DoctorViewModel", "Exception")
                Log.d("DoctorViewModel", e.message.toString())
                Log.d("DoctorViewModel", e.stackTraceToString())
                _uiState.value = VisionUiState(Error = "Exception error: ${e.message}")
            }
        }
    }

    /**
     * Select doctor
     *
     * @param doctor
     */
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



