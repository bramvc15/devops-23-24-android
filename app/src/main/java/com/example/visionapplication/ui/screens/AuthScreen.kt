package com.example.visionapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.visionapplication.R
import com.example.visionapplication.ui.views.MainViewModel

/**
 * Auth screen
 *
 * @param mainViewModel
 */
@Composable
fun AuthScreen(mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val title = if (mainViewModel.userIsAuthenticated) {
            stringResource(R.string.logged_in_title)
        } else {
            if (mainViewModel.appJustLaunched) {
                stringResource(R.string.initial_title)
            } else {
                stringResource(R.string.logged_out_title)
            }
        }
        Title(
            text = title
        )
        if (mainViewModel.userIsAuthenticated) {
            UserInfoRow(
                label = stringResource(R.string.name_label),
                value = mainViewModel.authedDoctor.name,
            )
            UserInfoRow(
                label = stringResource(R.string.email_label),
                value = mainViewModel.authedDoctor.email,
            )
            UserPicture(
                url = mainViewModel.authedDoctor.picture,
                description = mainViewModel.authedDoctor.name,
            )
        }
        val buttonText: String
        val onClickAction: () -> Unit
        if (mainViewModel.userIsAuthenticated) {
            buttonText = stringResource(R.string.log_out_button)
            onClickAction = { mainViewModel.logout() }
        } else {
            buttonText = stringResource(R.string.log_in_button)
            onClickAction = { mainViewModel.login() }
        }
        LogButton(
            text = buttonText,
            onClick = onClickAction,
        )
    }
}

/**
 * Title
 *
 * @param text
 */
@Composable
fun Title(
    text: String,
)
{
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
    )
}

/**
 * User info row
 *
 * @param label
 * @param value
 */
@Composable
fun UserInfoRow(
    label: String,
    value: String,
) {
    Row {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        )
        Spacer(
            modifier = Modifier.width(10.dp),
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
            )
        )
    }
}

/**
 * User picture
 *
 * @param url
 * @param description
 */
@Composable
fun UserPicture(
    url: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize(0.5f),
        )
    }
}

/**
 * Log button
 *
 * @param text
 * @param onClick
 * @receiver
 */
@Composable
fun LogButton(
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
            )
        }
    }
}