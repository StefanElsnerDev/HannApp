package com.example.hannapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.ui.theme.HannAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "",
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun AppTopBar_LightMode() {
    HannAppTheme {
        AppTopBar(
            title = "Title",
            navigationIcon = { Icon(Icons.Filled.ArrowBack, "") }

        ) {
            Icon(Icons.Filled.Edit, "")
            Icon(Icons.Filled.Delete, "")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AppTopBar_DarkMode() {
    HannAppTheme {
        AppTopBar(
            title = "Title",
            navigationIcon = { Icon(Icons.Filled.ArrowBack, "") }

        ) {
            Icon(Icons.Filled.Edit, "")
            Icon(Icons.Filled.Delete, "")
        }
    }
}
