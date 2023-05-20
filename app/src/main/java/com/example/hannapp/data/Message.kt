package com.example.hannapp.data

import androidx.annotation.StringRes

data class Message(
    @StringRes val messageRes: Int?,
    val message: String? = null
)
