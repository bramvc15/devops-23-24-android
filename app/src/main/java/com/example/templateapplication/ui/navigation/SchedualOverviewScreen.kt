package com.example.templateapplication.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.templateapplication.R

enum class SchedualOverviewScreen(@StringRes val title: Int, val icon: ImageVector) {
    Start(title = R.string.app_name, icon = Icons.Filled.Check),
    Detail(title = R.string.detail, Icons.Filled.Check),
    About(title = R.string.about_title, icon = Icons.Filled.Info),
}