package com.example.hannapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.ui.button.Button
import com.example.hannapp.ui.input.QuantityInput
import com.example.hannapp.ui.selection.DropDown
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun App() {
    HannAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when(LocalConfiguration.current.orientation){
                Configuration.ORIENTATION_LANDSCAPE -> LandScapeSelectionScreen()
                else -> PortraitSelectionScreen()
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun LandScapeSelectionScreen() {
    HannAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PortraitSelectionScreen()
            }
        }
    }
}

@Preview(showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait"
)
@Composable
fun PortraitSelectionScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        DropDown(
            modifier = Modifier
                .fillMaxWidth()
        )
        QuantityInput(
            modifier = Modifier
                .wrapContentSize()
        )
        Button(
            modifier = Modifier
                .wrapContentSize(),
            icon = Icons.Filled.Add
        )
    }
}
