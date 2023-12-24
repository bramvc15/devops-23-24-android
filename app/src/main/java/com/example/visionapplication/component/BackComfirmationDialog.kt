package com.example.visionapplication.component


import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


/**
 * Back confirmation dialog
 *
 * @param onBackPressedDispatcher
 * @param onConfirmed
 * @param onCancel
 * @receiver
 * @receiver
 */
@Composable
fun BackConfirmationDialog(
    onBackPressedDispatcher: OnBackPressedDispatcher,
    onConfirmed: () -> Unit,
    onCancel: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    DisposableEffect(onBackPressedDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openDialog.value = true
            }
        }
        onBackPressedDispatcher.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text("Confirmation") },
            text = { Text("Do you really want to go back to Selection Screen?") },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onConfirmed()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onCancel()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
