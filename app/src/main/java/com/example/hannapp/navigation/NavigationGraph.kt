package com.example.hannapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hannapp.ui.screens.NutrimentLogScreen
import com.example.hannapp.ui.screens.NutritionDataUpdateScreen
import com.example.hannapp.ui.screens.NutritionInsertScreen
import com.example.hannapp.ui.viewmodel.NutritionInsertViewModel
import com.example.hannapp.ui.viewmodel.NutritionSelectViewModel
import com.example.hannapp.ui.viewmodel.NutritionUpdateViewModel

enum class Destination(val value: String) {
    DATA("data"),
    DATA_UPDATE("data_update"),
    SELECTION("selection")
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    isCompactScreen: Boolean
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.SELECTION.value) {
            val viewModel = hiltViewModel<NutritionSelectViewModel>()

            NutrimentLogScreen(
                viewModel = viewModel,
                navController = navController,
                isCompactScreen = isCompactScreen
            )
        }
        composable(Destination.DATA.value) {
            val viewModel = hiltViewModel<NutritionInsertViewModel>()

            NutritionInsertScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Destination.DATA_UPDATE.value) {
            val viewModel = hiltViewModel<NutritionUpdateViewModel>() // TODO: Necessary with HILT?

            NutritionDataUpdateScreen(
                viewModel = viewModel
            )
        }
    }
}
