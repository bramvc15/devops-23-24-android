package com.example.visionapplication

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.visionapplication.data.Notes.FakeNoteRepository
import com.example.visionapplication.model.Note
import com.example.visionapplication.ui.screens.notes.NoteScreen
import com.example.visionapplication.ui.views.NoteViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NoteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun noteScreen_addNoteAndVerify() {

        val note1 = Note(1, "test1", "test1")
        val note2 = Note(2, "test2", "test2")
        val noteList = mutableListOf(note1, note2)

        val noteViewModel = NoteViewModel(FakeNoteRepository(noteList))

        composeTestRule.setContent {
            NoteScreen(noteViewModel = noteViewModel)
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Your notes").assertExists()

        composeTestRule.onNodeWithContentDescription("Add_note").performClick()

        composeTestRule.onNodeWithTag("Title").performTextInput("Test Note Title")
        composeTestRule.onNodeWithTag("Content").performTextInput("Test Note Content")

        composeTestRule.onNodeWithTag("Add Button").performClick()

        composeTestRule.onNodeWithText("Test Note Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Note Content").assertIsDisplayed()
    }

    @Test
    fun noteScreen_deleteNoteAndVerify(){

        val note1 = Note(1, "Test1", "Test")
        val note2 = Note(2, "test2", "test2")
        val noteList = mutableListOf(note1, note2)

        val noteViewModel = NoteViewModel(FakeNoteRepository(noteList))

        composeTestRule.setContent {
            NoteScreen(noteViewModel = noteViewModel)
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Test1").assertIsDisplayed()

        composeTestRule.onNodeWithTag("knop Test1").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("Test1").assertDoesNotExist()


    }

    @Test
    fun noteScreen_addNote_DeleteNote_verify(){
        val note1 = Note(1, "Test1", "Test")
        val note2 = Note(2, "test2", "test2")
        val noteList = mutableListOf(note1, note2)

        val noteViewModel = NoteViewModel(FakeNoteRepository(noteList))

        composeTestRule.setContent {
            NoteScreen(noteViewModel = noteViewModel)
        }

        composeTestRule.onNodeWithContentDescription("Add_note").performClick()

        composeTestRule.onNodeWithTag("Title").performTextInput("Test Note Title")
        composeTestRule.onNodeWithTag("Content").performTextInput("Test Note Content")

        composeTestRule.onNodeWithTag("Add Button").performClick()

        composeTestRule.onNodeWithText("Test Note Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Note Content").assertIsDisplayed()

        composeTestRule.waitForIdle()


        composeTestRule.onNodeWithTag("knop Test Note Title").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("Test Note Title").assertDoesNotExist()

    }
}