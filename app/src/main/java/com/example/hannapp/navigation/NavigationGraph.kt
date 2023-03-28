package com.example.hannapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.output.CalculationScreen
import com.example.hannapp.ui.screens.NutritionInsertScreen
import com.example.hannapp.ui.screens.NutritionDataUpdateScreen
import com.example.hannapp.ui.selection.SelectionScreen
import com.example.hannapp.ui.viewmodel.NutritionInsertViewModel
import com.example.hannapp.ui.viewmodel.NutritionUpdateViewModel
import com.example.hannapp.ui.viewmodel.NutritionViewModel

enum class Destination(val value: String) {
    CALCULATION("calculation"),
    DATA("data"),
    DATA_UPDATE("data_update"),
    SELECTION("selection")
}

@Composable
fun NavigationGraph (
    navController: NavHostController,
    startDestination: String
){
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.SELECTION.value) {
            val viewModel = hiltViewModel <NutritionViewModel>()

            SelectionScreen(
                viewModel = viewModel,
                onAdd = {
                    /*TODO 'it' used by viewModel*/
                    navigationActions.navigateToCalculation
                },
                navController = navController
            )
        }
        composable(Destination.CALCULATION.value) {
            CalculationScreen(
                mood = Mood.GREEN,
                navController = navController
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