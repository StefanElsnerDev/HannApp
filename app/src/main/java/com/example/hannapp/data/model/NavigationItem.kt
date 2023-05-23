package com.example.hannapp.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavigationItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val destination: String = "",
    val action: () -> Unit = {}
)
