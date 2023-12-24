package com.example.visionapplication.ui.screens.notes.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


/**
 * Add note dialog
 *
 * @param onDismiss
 * @param onNoteAdded
 * @receiver
 * @receiver
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onNoteAdded: (title: String, content: String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.95f)
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(400.dp)
                        .height(300.dp)
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    Scaffold(
                        content = {
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
                                        .testTag("Title")
                                )

                                OutlinedTextField(
                                    value = content,
                                    onValueChange = { content = it },
                                    label = { Text("Content") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .testTag("Content")
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    modifier = Modifier.testTag("Add Button"),
                                    onClick = {
                                        if (title.isNotBlank() || content.isNotBlank()) {
                                            onNoteAdded(title, content)
                                            onDismiss()
                                        }
                                    }
                                ) {
                                    Text("Add Note")
                                }
                            }
                        }
                    )
                }
            }
        )
    }
}