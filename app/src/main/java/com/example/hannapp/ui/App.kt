package com.example.hannapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.navigation.Destination
import com.example.hannapp.navigation.NavigationGraph
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun App() {
    HannAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            NavigationGraph(
                navController = navController,
                startDestination = Destination.SELECTION.value,
                onIndexSelected = {}
            )
        }
    }
}
