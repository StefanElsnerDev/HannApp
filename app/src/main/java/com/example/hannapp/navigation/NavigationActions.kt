package com.example.hannapp.navigation

import androidx.navigation.NavHostController

class NavigationActions(navController: NavHostController) {
    val navigateToData: () -> Unit = {navController.navigate(Destination.DATA.value)}
    val navigateToCalculation: () -> Unit = { navController.navigate(Destination.CALCULATION.value) }
    val navigateToSelection: () -> Unit = { navController.navigate(Destination.SELECTION.value) }
}
