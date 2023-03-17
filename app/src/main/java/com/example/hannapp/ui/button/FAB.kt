package com.example.hannapp.ui.button

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FAB(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        contentColor = MaterialTheme.colorScheme.onBackground,
        content = { Icon(imageVector, "") },
        onClick = onClick
    )
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun FAB_LightMode() {
    FAB(imageVector = Icons.Filled.Add){}
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FAB_DarkMode() {
    FAB(imageVector = Icons.Filled.Add){}
}
