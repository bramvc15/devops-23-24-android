package com.example.visionapplication

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.visionapplication.model.AuthedDoctor
import com.example.visionapplication.ui.screens.AuthScreen
import com.example.visionapplication.ui.views.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun authScreen_initialState() {
        composeTestRule.setContent {
            AuthScreen(mainViewModel)
        }

        composeTestRule.onNodeWithText("Welcome to the app!").assertExists()
        composeTestRule.onNodeWithText("Log In").assertExists()
    }

    @Test
    fun authScreen_authenticatedState() {

        mainViewModel.userIsAuthenticated = true
        mainViewModel.authedDoctor = AuthedDoctor("")

        composeTestRule.setContent {
            AuthScreen(mainViewModel)
        }

        composeTestRule.onNodeWithText("You’re logged in!").assertExists()
        composeTestRule.onNodeWithText("Log Out").assertExists()
        composeTestRule.onNodeWithText("name", ignoreCase = true).assertExists()
        composeTestRule.onNodeWithText("email", ignoreCase = true).assertExists()
    }

}