package com.example.hannapp.navigation

import androidx.navigation.NavHostController

class NavigationActions(navController: NavHostController) {
    val navigateToCalculation: () -> Unit = { navController.navigate(Destination.CALCULATION.value) }
}