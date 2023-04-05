package com.example.hannapp.navigation

import androidx.navigation.NavHostController

class NavigationActions(navController: NavHostController) {
    val navigateToData: () -> Unit = { navController.navigate(Destination.DATA.value) }
    val navigateToUpdateData: () -> Unit = { navController.navigate(Destination.DATA_UPDATE.value) }
    val navigateToSelection: () -> Unit = { navController.navigate(Destination.SELECTION.value) }
}
