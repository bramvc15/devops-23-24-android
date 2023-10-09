package com.example.templateapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.ui.theme.VisionApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisionApplicationTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                VisionApp(windowSizeClass)
            }
        }
    }
}

@Composable
private fun HomeIntroSection(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Vision Oogcentrum",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Welkom bij Vision Oogcentrum. Wij zijn hier om voor uw ooggezondheid te zorgen.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {

                }
            ) {
                Text(
                    text = "Maak afspraak",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {

                }
            ) {
                Text(
                    text = "Contacteer ons",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                    )
            }
        }
    }
}

@Composable
private fun NewsItem(
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


@Composable
private fun HomeNewsSection(modifier: Modifier = Modifier) {
    Text(
        text = "Actualiteit binnen onze praktijk",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        NewsItem(
            title = stringResource(R.string.news_blog_title_1),
            description = stringResource(R.string.news_blog_description_1),
            imageResId = R.drawable.oog,
        )
        Spacer(modifier = Modifier.height(16.dp))
        NewsItem(
            title = stringResource(R.string.news_blog_title_2),
            description = stringResource(R.string.news_blog_description_2),
            imageResId = R.drawable.oog
        )
    }
}


@Composable
private fun BottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.bottom_navigation_home)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.bottom_navigation_behandelingen)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.bottom_navigation_afspraak)
                )
            }
        )
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(16.dp))
        HomeIntroSection()
        HomeNewsSection()
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun VisionAppPortrait() {
    VisionApplicationTheme {
        Scaffold(
            bottomBar = { BottomNavigation() }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun VisionApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            VisionAppPortrait()
        }
        WindowWidthSizeClass.Expanded -> {
            //VisionAppLandscape()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeIntroSectionPreview() {
    HomeIntroSection()
}

@Preview(showBackground = true)
@Composable
fun HomeNewsSectionPreview() {
    HomeNewsSection()
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationPreview() {
    VisionApplicationTheme { BottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun ScreenContentPreview() {
    VisionApplicationTheme { HomeScreen() }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun VisionPortraitPreview() {
    VisionAppPortrait()
}
