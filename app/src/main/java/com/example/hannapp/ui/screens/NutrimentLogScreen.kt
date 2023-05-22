package com.example.hannapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.R
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.button.FAB
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.AppTopBar
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.components.SnackBar
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.output.CalculationContent
import com.example.hannapp.ui.output.LogGroup
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.Constraints.SPACE_VERTICAL
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutrimentSelectUiState
import com.example.hannapp.ui.viewmodel.NutritionSelectViewModel
import com.example.hannapp.utils.WindowSize
import kotlinx.coroutines.flow.flowOf

@Composable
fun NutrimentLogContent(
    modifier: Modifier,
    uiState: NutrimentSelectUiState,
    pagingItems: LazyPagingItems<NutritionUiModel>,
    loggedNutriments: List<NutrimentUiLogModel>,
    quantity: String,
    onQuantityChanged: (String) -> Unit,
    isEditMode: Boolean,
    isCompactScreen: Boolean,
    onAdd: () -> Unit,
    navController: NavHostController,
    onClickBoxClick: () -> Unit,
    selectedNutriment: NutritionUiModel,
    onNutrimentSelected: (NutritionUiModel) -> Unit,
    onLoggedNutrimentSelected: (NutrimentUiLogModel) -> Unit,
    onSaveEdit: () -> Unit = {},
    onAbort: () -> Unit = {},
    clear: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val error = uiState.errorMessage

    AppScaffold(
        topBar = {
            AppTopBar(
                title = if(isEditMode) stringResource(id = R.string.edit_mode) else ""
            ) {
                when (isEditMode) {
                    true -> {
                        IconButton(
                            onClick = {
                                onAbort()
                                focusManager.clearFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }

                        IconButton(
                            onClick = {
                                onSaveEdit()
                                focusManager.clearFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    false -> {
                        IconButton(onClick = { clear() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.restore),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
        bottomBar = { NavigationBar(navController) },
        snackBarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    data.visuals.actionLabel?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = PADDING),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            SnackBar(
                                message = data.visuals.message,
                                actionLabel = it,
                                onAction = { data.dismiss() },
                            )
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            if (!isEditMode) {
                FAB({ Icon(Icons.Default.Add, null) }) {
                    onAdd()
                    focusManager.clearFocus()
                }
            }
        }
    ) { paddingValues ->

        when (isCompactScreen) {
            true -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    LogGroup(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 0.dp,
                                max = WindowSize.COMPACT.size),
                        uiState = uiState,
                        onClickBoxClick = onClickBoxClick,
                        quantity = quantity,
                        onQuantityChanged = onQuantityChanged,
                        focusManager = focusManager,
                        onAdd = onAdd,
                        selectedNutriment = selectedNutriment,
                        onNutrimentSelected = onNutrimentSelected,
                        pagingItems = pagingItems,
                        loggedNutriments = loggedNutriments,
                        onLoggedNutrimentSelected = onLoggedNutrimentSelected,
                    )

                    CalculationContent(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = PADDING),
                        mood = Mood.GREEN
                    )
                }
            }

            false -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LogGroup(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        uiState = uiState,
                        onClickBoxClick = onClickBoxClick,
                        quantity = quantity,
                        onQuantityChanged = onQuantityChanged,
                        focusManager = focusManager,
                        onAdd = onAdd,
                        selectedNutriment = selectedNutriment,
                        onNutrimentSelected = onNutrimentSelected,
                        pagingItems = pagingItems,
                        loggedNutriments = loggedNutriments,
                        onLoggedNutrimentSelected = onLoggedNutrimentSelected,
                    )

                    Spacer(modifier = Modifier.height(SPACE_VERTICAL))

                    CalculationContent(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = PADDING),
                        mood = Mood.GREEN
                    )
                }
            }
        }

    }
    if (error != null) {
        val label = stringResource(id = R.string.okay)
        val errorMessage =
            error.messageRes?.let { stringResource(id = it) } ?: error.message ?: ""

        LaunchedEffect(error) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = label,
            )
        }
    }
}

@Composable
fun NutrimentLogScreen(
    viewModel: NutritionSelectViewModel = hiltViewModel(),
    navController: NavHostController,
    isCompactScreen: Boolean,
) {
    val uiState by viewModel.uiState.collectAsState()
    val nutriments = viewModel.nutriments.collectAsLazyPagingItems()
    val logged by viewModel.nutrimentLog.collectAsStateWithLifecycle()

    NutrimentLogContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        pagingItems = nutriments,
        loggedNutriments = logged,
        quantity = uiState.quantity,
        onQuantityChanged = { viewModel.setQuantity(it) },
        isEditMode = uiState is NutrimentSelectUiState.EditLogUiState,
        isCompactScreen = isCompactScreen,
        onAdd = { viewModel.add() },
        navController = navController,
        onClickBoxClick = { viewModel.getAll() },
        selectedNutriment = uiState.nutritionUiModel,
        onNutrimentSelected = { viewModel.select(it) },
        onLoggedNutrimentSelected = { viewModel.edit(it) },
        onSaveEdit = { viewModel.update() },
        onAbort = { viewModel.abort() },
        clear = { viewModel.clearAll() }
    )
}

private val dummyList = listOf(
    NutrimentUiLogModel(
        id = 1,
        nutrition = NutritionUiModel(
            name = "Peach"
        ),
        quantity = 123.4,
        unit = "g",
        createdAt = 1681801313,
        modifiedAt = 1999801313,
    ),
    NutrimentUiLogModel(
        id = 2,
        nutrition = NutritionUiModel(
            name = "Apple"
        ),
        quantity = 123.4,
        unit = "g",
        createdAt = 1681801313,
        modifiedAt = null
    ),
    NutrimentUiLogModel(
        id = 3,
        nutrition = NutritionUiModel(
            name = "Chocolate"
        ),
        quantity = 123.4,
        unit = "g",
        createdAt = 1681801313,
        modifiedAt = 1999801313
    )
)

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun NutrimentLogScreen_LightMode() {
    HannAppTheme {
        NutrimentLogContent(
            modifier = Modifier,
            uiState = NutrimentSelectUiState.LogUiState(),
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            loggedNutriments = dummyList,
            quantity = "12.34",
            onQuantityChanged = {},
            isEditMode = false,
            isCompactScreen = false,
            onAdd = {},
            onClickBoxClick = {},
            navController = rememberNavController(),
            selectedNutriment = NutritionUiModel(),
            onNutrimentSelected = {},
            onLoggedNutrimentSelected = {},
            clear = {})
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
fun NutrimentLogScreen_Compact_LightMode() {
    HannAppTheme {
        NutrimentLogContent(
            modifier = Modifier,
            uiState = NutrimentSelectUiState.LogUiState(),
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            loggedNutriments = dummyList,
            quantity = "12.34",
            onQuantityChanged = {},
            isEditMode = false,
            isCompactScreen = true,
            onAdd = {},
            onClickBoxClick = {},
            navController = rememberNavController(),
            selectedNutriment = NutritionUiModel(),
            onNutrimentSelected = {},
            onLoggedNutrimentSelected = {},
            clear = {})
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun NutrimentLogScreen_EditMode_LightMode() {
    HannAppTheme {
        NutrimentLogContent(
            modifier = Modifier,
            uiState = NutrimentSelectUiState.LogUiState(),
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            loggedNutriments = dummyList,
            quantity = "12.34",
            onQuantityChanged = {},
            isEditMode = true,
            isCompactScreen = false,
            onAdd = {},
            onClickBoxClick = {},
            navController = rememberNavController(),
            selectedNutriment = NutritionUiModel(),
            onNutrimentSelected = {},
            onLoggedNutrimentSelected = {},
            clear = {})
    }
}
