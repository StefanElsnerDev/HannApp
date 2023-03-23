package com.example.hannapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.output.CalculationScreen
import com.example.hannapp.ui.screens.NutritionDataScreen
import com.example.hannapp.ui.selection.SelectionScreen
import com.example.hannapp.ui.viewmodel.NutritionDataViewModel
import com.example.hannapp.ui.viewmodel.NutritionViewModel

enum class Destination(val value: String) {
    SELECTION("selection"),
    CALCULATION("calculation"),
    DATA("data")
}

@Composable
fun NavigationGraph (
    navController: NavHostController,
    startDestination: String,
    onIndexSelected: (Int) -> Unit
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
            val viewModel = hiltViewModel<NutritionDataViewModel>()

            NutritionDataScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}