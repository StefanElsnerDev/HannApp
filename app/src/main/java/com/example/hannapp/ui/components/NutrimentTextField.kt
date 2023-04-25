package com.example.hannapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutrimentTextField(
    modifier: Modifier,
    text: String,
    label: @Composable() (() -> Unit),
) {
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = { _ -> },
        label = label,
        textStyle = MaterialTheme.typography.labelMedium,
        enabled = false
    )
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun NutrimentTextField_LightMode() {
    NutrimentTextField(
        modifier = Modifier
            .width(200.dp)
            .height(50.dp),
        text = "Important Text",
        label = { Text(text = "Label") },
    )
}
