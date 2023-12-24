package com.example.visionapplication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.visionapplication.data.Appointments.FakeAppointmentRepository
import com.example.visionapplication.data.Doctors.FakeDoctorRepository
import com.example.visionapplication.data.TimeSlots.FakeTimeSlotRepository
import com.example.visionapplication.model.Appointment
import com.example.visionapplication.model.Doctor
import com.example.visionapplication.model.Patient
import com.example.visionapplication.model.TimeSlot
import com.example.visionapplication.ui.screens.calendarweek.CalendarWeekScreen
import com.example.visionapplication.ui.views.AppointmentViewModel
import com.example.visionapplication.ui.views.DoctorViewModel
import com.example.visionapplication.ui.views.TimeSlotViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalenderWeekScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup(){

        val doctor1 = Doctor(1, "Guillaume", " tester", 1, "not liking this", true, ",", "")
        val patient1 = Patient(1, "The Cure", "mail","696969", "11-12",1,1)
        val appointment1 = Appointment(1,"reason", "note", patient1 )
        val timeSlot1 = TimeSlot(1,1,0, "2023-12-24T08:00:00.0000000",15 ,appointment1 )

        val doctor2 = Doctor(2, "tester", " tester", 1, "not liking this", true, ",", "")
        val patient2 = Patient(2, "Depeche Mode", "mail","696969", "11-12",1,1)
        val appointment2 = Appointment(2,"reason", "note", patient2 )
        val timeSlot2 = TimeSlot(2,2,0, "2023-12-24T08:00:00.0000000",15 ,appointment2 )

        val doctorList = mutableListOf(doctor1, doctor2)
        val doctorViewModel = DoctorViewModel(FakeDoctorRepository(doctorList))

        val timeSlotList = mutableListOf(timeSlot1, timeSlot2)
        val timeSlotViewModel = TimeSlotViewModel(FakeTimeSlotRepository(timeSlotList))

        val appointmentList = mutableListOf(appointment1, appointment2)
        val appointmentViewModel = AppointmentViewModel(FakeAppointmentRepository(appointmentList))

        composeTestRule.setContent{

            CalendarWeekScreen(
                doctorViewModel = doctorViewModel,
                timeslotViewModel = timeSlotViewModel,
                appointmentViewModel = appointmentViewModel,
            )

        }

    }

    @Test
    fun calenderWeekScreen_changeDoctor_verifyAppointment(){

        composeTestRule.onNodeWithTag("maand").assertExists()

        composeTestRule.onNodeWithContentDescription("Dropdown").assertExists()
        composeTestRule.onNodeWithContentDescription("Dropdown").performClick()

        composeTestRule.onNodeWithText("tester").assertExists()
        composeTestRule.onNodeWithText("tester").performClick()

        composeTestRule.onNodeWithText("Depeche Mode").assertExists()
    }

    @Test
    fun calenderWeekScreen_EditAppointment_verify(){
        composeTestRule.onNodeWithContentDescription("Dropdown").performClick()
        composeTestRule.onNodeWithText("Guillaume").assertExists().performClick()


        composeTestRule.onNodeWithText("The Cure").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Edit", ignoreCase = true).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Note").performTextInput("Test")
        composeTestRule.onNodeWithText("Reason").performTextInput("Test")

        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("The Cure").performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
        composeTestRule.onNodeWithText("Testnote").assertIsDisplayed()
        composeTestRule.onNodeWithText("Testreason").assertIsDisplayed()

    }
    @Test
    fun calenderWeekScreen_DeleteAppointment_cancelVerify(){


        composeTestRule.onNodeWithContentDescription("Dropdown").performClick()
        composeTestRule.onNodeWithText("Guillaume").assertExists().performClick()

        composeTestRule.onNodeWithText("The Cure").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Cancel", ignoreCase = true).performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(4000)

        composeTestRule.onNodeWithText("Nee").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("The Cure").assertExists()


    }

    @Test
    fun calenderWeekScreen_DeleteAppointment_Verify(){


        composeTestRule.onNodeWithContentDescription("Dropdown").performClick()
        composeTestRule.onNodeWithText("Guillaume").assertExists().performClick()


        composeTestRule.onNodeWithText("The Cure").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Cancel", ignoreCase = true).performClick()
        composeTestRule.waitForIdle()

        Thread.sleep(4000)

        composeTestRule.onNodeWithText("Ja").performClick()

        composeTestRule.waitForIdle()

        Thread.sleep(4000)
        composeTestRule.onNodeWithText("The Cure").assertExists()
    }
}