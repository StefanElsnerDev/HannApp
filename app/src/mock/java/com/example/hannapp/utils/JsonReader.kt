package com.example.hannapp.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonReader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun jsonToString(fileName: String): String = context.assets.open(fileName)
        .bufferedReader()
        .use { it.readText() }
}
