package com.example.hannapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.hannapp.ui.components.ReferenceContent
import com.example.hannapp.ui.viewmodel.NutritionLimitViewModel

@Composable
fun ReferenceScreen(
    viewModel: NutritionLimitViewModel,
    navController: NavHostController,
    isCompactScreen: Boolean
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    ReferenceContent(
        uiState = uiState,
        event = { viewModel.event(it) },
        isCompactScreen = isCompactScreen,
        navController = navController,
        modifier = Modifier.fillMaxSize()
    )
}
