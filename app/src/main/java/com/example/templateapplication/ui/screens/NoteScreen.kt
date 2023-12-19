package com.example.templateapplication.ui.screens

//import com.example.templateapplication.shared.generateNotes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.model.Note

@Composable
fun NoteScreen() {
    var newNoteTitle by remember { mutableStateOf("") }
    var newNoteContent by remember { mutableStateOf("") }
    var isAddingNote by remember { mutableStateOf(false) }

//    var notes by remember { mutableStateOf(generateNotes()) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Button to add a new note
//        Button(
//            onClick = { isAddingNote = true },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp)
//        ) {
//            Text("Add New Note")
//        }
//
//
//        if (isAddingNote) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                elevation = 8.dp
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//                    OutlinedTextField(
//                        value = newNoteTitle,
//                        onValueChange = { newNoteTitle = it },
//                        label = { Text("Title") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 8.dp)
//                    )
//                    OutlinedTextField(
//                        value = newNoteContent,
//                        onValueChange = { newNoteContent = it },
//                        label = { Text("Content") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 8.dp)
//                    )
//
//                    Button(
//                        onClick = {
//                            if (newNoteTitle.isNotBlank() && newNoteContent.isNotBlank()) {
//
//                                val newNote = Note(
//                                    id = 1,
//                                    title = newNoteTitle,
//                                    content = newNoteContent,
//                                    timestamp = System.currentTimeMillis()
//                                )
//
//                                notes = notes.toMutableList().apply { add(newNote) }
//
//                                newNoteTitle = ""
//                                newNoteContent = ""
//
//                                isAddingNote = false
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 8.dp)
//                    ) {
//                        Text("Save")
//                    }
//                }
//            }
//        }
//
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            items(notes) { note ->
//                NoteItem(note = note, onDeleteClick = {
//
//                })
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//    }
}


@Composable
private fun NoteItem(note: Note, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Created on ${
//                    SimpleDateFormat(
//                        "dd/MM/yyyy",
//                        Locale.getDefault()
//                    ).format(note.timestamp)
//                }",
//                fontSize = 12.sp,
//                color = Color.Gray
//            )
            Spacer(modifier = Modifier.height(8.dp))
            // Button to delete the note
            Button(onClick = onDeleteClick) {
                Text("Delete")
            }
        }
    }
}

