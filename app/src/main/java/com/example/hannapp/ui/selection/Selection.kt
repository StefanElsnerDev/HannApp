package com.example.hannapp.ui.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.R
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.ui.button.Button
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.dropdown.DropDownDialog
import com.example.hannapp.ui.dropdown.SimpleDropDownItem
import com.example.hannapp.ui.input.InputField
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.output.CalculationScreen
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionSelectViewModel
import com.example.hannapp.ui.viewmodel.NutritionUiState
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionContent(
    uiState: NutritionUiState,
    pagingItems: LazyPagingItems<Nutrition>,
    onAdd: (String) -> Unit,
    navController: NavHostController,
    onClickBoxClick: ()->Unit,
    onItemSelected: (String) -> Unit
) {
    val snackBarHost = remember { SnackbarHostState() }

    AppScaffold(
        bottomBar = { NavigationBar(navController) },
        snackBarHost = { SnackbarHost(hostState = snackBarHost) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                var input by rememberSaveable { mutableStateOf("") }
                var selectedItem by rememberSaveable { mutableStateOf("") }
                var expanded by remember { mutableStateOf(false) }

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

                Box(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    OutlinedTextField(
                        value = selectedItem.ifBlank { "No Data" },
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        readOnly = true,
                        textStyle = MaterialTheme.typography.titleMedium,
                        label = { Text(text = "Auswahl") },
                        trailingIcon = {
                            if (!expanded) Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    )

                    //ClickBox
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onClickBoxClick()
                                expanded = true
                            },
                        color = Color.Transparent,
                    ) {}
                }

                if (expanded) {
                    DropDownDialog(
                        pagingItems = pagingItems,
                        onDismiss = { expanded = false },
                        itemContent = {
                            SimpleDropDownItem(
                                item = it,
                                onClick = { item ->
                                    onItemSelected(item.toString())
                                    selectedItem = item.toString()
                                    expanded = false
                                }
                            )
                        }
                    )
                }
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
            CalculationScreen(
                modifier = Modifier.fillMaxWidth(0.5f).padding(32.dp),
                mood = Mood.GREEN
            )
        }
    }
}

@Composable
fun SelectionScreen(
    viewModel: NutritionSelectViewModel = hiltViewModel(),
    onAdd: (String) -> Unit = {},
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val nutriments = viewModel.nutriments.collectAsLazyPagingItems()

    SelectionContent(
        uiState = uiState,
        pagingItems = nutriments,
        onAdd = onAdd,
        navController = navController,
        onClickBoxClick = { viewModel.getAll() },
        onItemSelected = { }
    )
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun SelectionContent_LightMode() {
    HannAppTheme {
        SelectionContent(
            uiState = NutritionUiState(),
            pagingItems = flowOf(PagingData.from(listOf(Nutrition()))).collectAsLazyPagingItems(),
            onAdd = {},
            onClickBoxClick = {},
            navController = rememberNavController(),
            onItemSelected = {}
        )
    }
}
