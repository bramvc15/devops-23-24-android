package com.example.templateapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
public fun NewsItem(
    title: String,
    description: String,
    imageResId: Int
) {
    Row {
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                modifier = Modifier.padding(top = 4.dp)
            )
            Button(
                onClick = {

                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Lees meer")
            }
        }
        Image(
            painter = painterResource(imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .weight(1f)
                .size(80.dp)
        )
    }
}
