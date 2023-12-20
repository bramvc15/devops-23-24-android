package com.example.templateapplication.ui.screens.notes.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onNoteAdded: (title: String, content: String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
                    .width(400.dp)
                    .height(300.dp)
            ) {
                Scaffold() {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var title by remember { mutableStateOf("") }
                        var content by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )

                       LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f) // Take remaining vertical space
                        ) {
                           item {
                               OutlinedTextField(
                                   value = content,
                                   onValueChange = { content = it },
                                   label = { Text("Content") },
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(8.dp)
                               )
                           }
                       }
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (title.isNotBlank() || content.isNotBlank()) {
                                    onNoteAdded(title, content)
                                }
                            }
                        ) {
                            Text("Add Note")
                        }
                    }
                }
            }
        }
    )
}