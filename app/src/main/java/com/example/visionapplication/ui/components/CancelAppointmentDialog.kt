package com.example.visionapplication.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Cancel appointment dialog
 *
 * @param onDismiss
 * @param onCancelAppointment
 * @receiver
 * @receiver
 */
@Composable
fun CancelAppointmentDialog(
    onDismiss: () -> Unit,
    onCancelAppointment: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text("Waarschuwing")
        },
        text = {
            Text("Weet je zeker dat je deze afspraak wilt annuleren?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    onCancelAppointment()
                }
            ) {
                Text("Ja")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Nee")
            }
        }
    )
}