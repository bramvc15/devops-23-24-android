package com.example.visionapplication


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.visionapplication.data.Doctors.FakeDoctorRepository
import com.example.visionapplication.model.Doctor
import com.example.visionapplication.ui.screens.DoctorSelectionScreen
import com.example.visionapplication.ui.views.DoctorViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DoctorSelectionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun doctorsVisible_verify(){


        val doctor1 = Doctor(1, "Guillaume", " tester", 1, "not liking this", true, ",", "")
        val doctor2 = Doctor(2, "tester", " tester", 1, "not liking this", true, ",", "")
        val doctorList = mutableListOf(doctor1, doctor2)

        val doctorViewModel = DoctorViewModel(FakeDoctorRepository(doctorList))


        composeTestRule.setContent {
            DoctorSelectionScreen(
                doctorViewModel= doctorViewModel,
                onNextButtonClicked = {

                    doctorViewModel.selectDoctor(doctor1)

                }
            )
        }

        composeTestRule.waitForIdle()

            //composeTestRule.onNodeWithText("Doctors").assertExists()
        composeTestRule.onNodeWithTag("Guillaume")

        Thread.sleep(3000)
    }

}