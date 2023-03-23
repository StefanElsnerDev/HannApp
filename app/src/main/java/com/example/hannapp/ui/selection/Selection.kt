package com.example.hannapp.ui.selection

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hannapp.R
import com.example.hannapp.ui.button.Button
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.input.InputField
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionUiState
import com.example.hannapp.ui.viewmodel.NutritionViewModel

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionContent(
    uiState: NutritionUiState = NutritionUiState(),
    onAdd: (String) -> Unit = {},
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
                var input by rememberSaveable{ mutableStateOf("") }

                uiState.errorMessage?.let {
                    val errorMessageText: String = uiState.errorMessage
                    val retryMessageText = stringResource(id = R.string.okay)

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
                    items = uiState.foodList.map { it.toString() } //TODO Remove Develop Output
                ) { onItemSelected(it) }
                InputField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .wrapContentSize(),
                    label = stringResource(id = R.string.quantity),
                    isError = false
                )
                Button(
                    modifier = Modifier
                        .wrapContentSize(),
                    icon = Icons.Filled.Add
                ) { onAdd(input) }
            }
        }
    }
}

@Composable
fun SelectionScreen(
    viewModel: NutritionViewModel = hiltViewModel(),
    onAdd: (String) -> Unit = {},
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    SelectionContent(
        uiState = uiState,
        onAdd = onAdd,
        navController = navController,
        onItemSelected = { }
    )
}
