package com.example.hannapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hannapp.ui.SelectionScreen
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.output.CalculationScreen

enum class Destination(val value: String) {
    SELECTION("selection"),
    CALCULATION("calculation")
}

@Composable
fun NavigationGraph (
    navController: NavHostController,
    startDestination: String,
    selectedIndex: Int,
    onAdd: () -> Unit,
    onIndexSelected: (Int) -> Unit
){
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.SELECTION.value) {
            SelectionScreen(
                selectedIndex = selectedIndex,
                onAdd = onAdd,
                onIndexSelected = onIndexSelected
            )
        }
        composable(Destination.CALCULATION.value) {
            CalculationScreen(
                mood = Mood.GREEN
            )
        }
    }
}