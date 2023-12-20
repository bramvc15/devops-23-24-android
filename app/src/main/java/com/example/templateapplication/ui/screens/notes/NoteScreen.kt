package com.example.templateapplication.ui.screens.notes

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.model.Note
import com.example.templateapplication.ui.screens.notes.components.AddNoteDialog
import com.example.templateapplication.ui.views.NoteViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(
    noteViewModel : NoteViewModel = viewModel(factory = NoteViewModel.Factory),
) {

    val notes by noteViewModel.notes.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var isDialogOpen by remember { mutableStateOf(false) }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isDialogOpen = true
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        )
        {
        Text("YOUR NOTES", fontSize = 40.sp, modifier = Modifier.padding(10.dp) )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {


            items(notes) { note ->
                NoteItem(note = note, onDeleteClicked = {
                    // Handle note deletion here
                    Log.d("note is here", "${note}")
                    noteViewModel.deleteNote(note)
                })
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        if (isDialogOpen) {
            AddNoteDialog(
                onDismiss = { isDialogOpen = false },
                onNoteAdded = { title, content ->
                    noteViewModel.insertNote(
                        Note(
                            title = title,
                            content = content
                        )
                    )
                    isDialogOpen = false
                }
            )
        }
        }
    }
}


@Composable
private fun NoteItem(note : Note, onDeleteClicked: () -> Unit){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colors.surface)
            .border(1.dp, MaterialTheme.colors.onSurface, shape = RectangleShape)
            .clip(MaterialTheme.shapes.medium),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = note.title, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = note.content, style = MaterialTheme.typography.body1)
        }

        IconButton(
            onClick = onDeleteClicked,

        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete note")
        }
    }

}

