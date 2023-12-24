package com.example.visionapplication.ui.screens.notes

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visionapplication.R
import com.example.visionapplication.model.Note
import com.example.visionapplication.ui.screens.notes.components.AddNoteDialog
import com.example.visionapplication.ui.views.NoteViewModel

/**
 * Note screen
 *
 * @param noteViewModel
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    noteViewModel : NoteViewModel = viewModel(factory = NoteViewModel.Factory),
) {

    val notes by noteViewModel.notes.collectAsState()
    var isDialogOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start

        )
        {
            Text(
                "Your notes",
                fontSize = 40.sp,
                modifier = Modifier.padding(10.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {


                items(notes) { note ->
                    AnimatedNoteItem(
                        note = note,
                        onDeleteClicked = { noteViewModel.deleteNote(note) }
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { isDialogOpen = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add_note")
        }


        AnimatedVisibility(
            visible = isDialogOpen,
            enter = fadeIn(),
            exit =  fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.95f)
            ) {
                AddNoteDialog(
                    onDismiss = {
                        isDialogOpen = false
                    },
                    onNoteAdded = { title, content ->
                        noteViewModel.insertNote(
                            Note(
                                title = title,
                                content = content
                            )
                        )

                        isDialogOpen = false
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedNoteItem(note : Note, onDeleteClicked: () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        NoteItem(note = note, onDeleteClicked = onDeleteClicked)
    }
}


/**
 * Note item
 *
 * @param note
 * @param onDeleteClicked
 * @receiver
 */
@Composable
fun NoteItem(note : Note, onDeleteClicked: () -> Unit){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(colorResource(id = R.color.noteColorYellow)),

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = note.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
                IconButton(
                    onClick = onDeleteClicked,
                    modifier = Modifier.testTag("knop "+note.title),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note ${note.title}"
                    )
                }
            }
        }
    }


