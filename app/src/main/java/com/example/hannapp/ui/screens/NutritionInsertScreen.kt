package com.example.hannapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.navigation.NavigationActions
import com.example.hannapp.ui.button.FAB
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.AppTopBar
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.input.NutritionDataGroup
import com.example.hannapp.ui.input.SearchBar
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.*

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionInsertContent(
    navController: NavHostController = rememberNavController(),
    nutritionModel: NutritionModel = NutritionModel(),
    uiComponents: List<NutritionComponent> = listOf(
        Name(),
        Kcal(),
        Protein(),
        Fad(),
        Carbohydrates(),
        Sugar(),
        Fiber(),
        Alcohol(),
        Energy()
    ),
    errors: Set<NutritionDataComponent> = emptySet(),
    showErrors: Boolean = false,
    onComponentValueChange: (NutritionComponent, String) -> Unit = { _, _ -> },
    onReset: (NutritionDataComponent) -> Unit = { _ -> },
    onAdd: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    HannAppTheme {
        AppScaffold(
            topBar = {
                AppTopBar() {
                    IconButton(
                        onClick = {
                            navigationActions.navigateToUpdateData()
                        }
                    ) { Icon(Icons.Filled.Edit, null) }
                }
            },
            bottomBar = { NavigationBar(navController) },
            floatingActionButton = {
                FAB({ Icon(Icons.Filled.Add, null) }) { onAdd() }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier.padding(horizontal = 48.dp),
                    onSearch = {
                        onSearch(it)
                    }
                )

                NutritionDataGroup(
                    onComponentValueChange = onComponentValueChange,
                    onReset = onReset,
                    uiComponents = uiComponents,
                    nutritionModel = nutritionModel,
                    errors = errors,
                    showErrors = showErrors
                )
            }
        }
    }
}

@Composable
fun NutritionInsertScreen(
    viewModel: NutritionInsertViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiComponents by viewModel.uiComponents.collectAsState()

    NutritionInsertContent(
        navController = navController,
        nutritionModel = uiState.nutrition,
        uiComponents = uiComponents,
        errors = uiState.errors,
        showErrors = uiState.showErrors,
        onComponentValueChange = { component, value ->
            viewModel.onNutritionChange(
                component,
                value
            )
            viewModel.validate()
        },
        onReset = { viewModel.resetError(it) },
        onAdd = {
            if (uiState.isValid) {
                viewModel.insert()
            } else {
                viewModel.validate()
                viewModel.showErrors()
            }
        }
    )
}
