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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.navigation.Destination
import com.example.hannapp.navigation.NavigationActions
import com.example.hannapp.navigation.NavigationGraph
import com.example.hannapp.ui.button.Button
import com.example.hannapp.ui.input.QuantityInput
import com.example.hannapp.ui.selection.DropDownField
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun App() {
    HannAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var selectedIndex by rememberSaveable { mutableStateOf(0) }

            val navController = rememberNavController()

            val navigationActions = remember(navController) {
                NavigationActions(navController)
            }

            NavigationGraph(navController = navController,
                startDestination = Destination.SELECTION.value,
                selectedIndex = selectedIndex,
                onAdd = navigationActions.navigateToCalculation,
                onIndexSelected = { selectedIndex = it }
            )
        }
    }
}

@Composable
fun SelectionScreen(selectedIndex: Int, onAdd: () -> Unit, onIndexSelected: (Int) -> Unit) {

    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> LandScapeSelectionScreen(
            selectedIndex = selectedIndex,
            onAdd = onAdd
        ) {
            onIndexSelected(it)
        }
        else -> PortraitSelectionScreen(selectedIndex = selectedIndex, onAdd = onAdd) {
            onIndexSelected(it)
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun LandScapeSelectionScreen(
    selectedIndex: Int = 999,
    onAdd: () -> Unit = {},
    onItemSelected: (Int) -> Unit = {}
) {
    HannAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PortraitSelectionScreen(
                    selectedIndex = selectedIndex,
                    onAdd = onAdd
                ) { onItemSelected(it) }
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Composable
fun PortraitSelectionScreen(
    selectedIndex: Int = 999,
    onAdd: () -> Unit = {},
    onItemSelected: (Int) -> Unit = {}) {
    HannAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                DropDownField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    selectedIndex = selectedIndex
                ){ onItemSelected(it) }
                QuantityInput(
                    modifier = Modifier
                        .wrapContentSize()
                )
                Button(
                    modifier = Modifier
                        .wrapContentSize(),
                    icon = Icons.Filled.Add
                ){ onAdd() }
            }
        }
    }
}
