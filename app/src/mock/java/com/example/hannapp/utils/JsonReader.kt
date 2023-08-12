package com.example.hannapp.utils

import android.content.Context

class JsonReader(
    private val context: Context
) {
    fun jsonToString(fileName: String): String = context.assets.open(fileName)
        .bufferedReader()
        .use { it.readText() }
}
