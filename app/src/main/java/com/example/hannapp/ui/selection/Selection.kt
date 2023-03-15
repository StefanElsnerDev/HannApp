package com.example.hannapp.ui.selection

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.ui.button.Button
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.input.QuantityInput
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionUiState
import com.example.hannapp.ui.viewmodel.NutritionViewModel

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionContent(
    uiState: NutritionUiState = NutritionUiState(),
    selectedIndex: Int = 0,
    onAdd: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    onItemSelected: (Int) -> Unit = {}
) {
    val snackBarHost = remember { SnackbarHostState() }

    HannAppTheme {
        AppScaffold(
            bottomBar = { NavigationBar(navController) },
            snackBarHost = { SnackbarHost(hostState = snackBarHost) }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                uiState.errorMessage?.let {
                    val errorMessageText: String = uiState.errorMessage
                    val retryMessageText = "Okay?!"

                    LaunchedEffect(errorMessageText, retryMessageText, snackBarHost) {
                        snackBarHost.showSnackbar(
                            message = errorMessageText,
                            actionLabel = retryMessageText
                        )
                    }
                }

                DropDownField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    items = uiState.nutritionNames,
                    selectedIndex = selectedIndex
                ) { onItemSelected(it) }
                QuantityInput(
                    modifier = Modifier
                        .wrapContentSize()
                )
                Button(
                    modifier = Modifier
                        .wrapContentSize(),
                    icon = Icons.Filled.Add
                ) { onAdd() }
            }
        }
    }
}

@Composable
fun SelectionScreen(
    viewModel: NutritionViewModel = hiltViewModel(),
    selectedIndex: Int,
    onAdd: () -> Unit = {},
    navController: NavHostController,
    onItemSelected: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    SelectionContent(
        uiState = uiState,
        selectedIndex = selectedIndex,
        onAdd = onAdd,
        navController = navController,
        onItemSelected = onItemSelected
    )
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Composable
fun PortraitSelectionScreen(
    selectedIndex: Int = 999,
    onAdd: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    onItemSelected: (Int) -> Unit = {}
) {
    HannAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PortraitSelectionScreen(
                    selectedIndex = selectedIndex,
                    onAdd = onAdd,
                    navController = navController
                ) { onItemSelected(it) }
            }
        }
    }
}