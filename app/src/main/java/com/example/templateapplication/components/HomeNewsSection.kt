package com.example.templateapplication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.components.NewsItem
import com.example.templateapplication.R

@Composable
public fun HomeNewsSection(modifier: Modifier = Modifier) {
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
