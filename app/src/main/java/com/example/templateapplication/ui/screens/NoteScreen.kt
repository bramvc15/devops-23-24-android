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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.model.Note

@Composable
fun NoteScreen() {


@Composable
fun NoteItem(note: Note, onDeleteClick: () -> Unit) {
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
        }}}}



