package com.example.hannapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.output.CalculationScreen
import com.example.hannapp.ui.selection.SelectionContent
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionSelectViewModel
import com.example.hannapp.ui.viewmodel.NutritionUiState
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutrimentLogContent(
    modifier: Modifier,
    uiState: NutritionUiState,
    pagingItems: LazyPagingItems<Nutrition>,
    onAdd: (String) -> Unit,
    navController: NavHostController,
    onClickBoxClick: () -> Unit,
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
            SelectionContent(
                modifier = Modifier.fillMaxWidth(0.5f),
                uiState,
                snackBarHost,
                onClickBoxClick,
                pagingItems,
                onItemSelected,
                onAdd
            )

            CalculationScreen(
                modifier = Modifier.fillMaxWidth(0.5f),
                mood = Mood.GREEN
            )
        }
    }
}

@Composable
fun NutrimentLogScreen(
    viewModel: NutritionSelectViewModel = hiltViewModel(),
    onAdd: (String) -> Unit = {},
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val nutriments = viewModel.nutriments.collectAsLazyPagingItems()

    NutrimentLogContent(
        modifier = Modifier.fillMaxSize(),
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
fun NutrimentLogScreen_LightMode() {
    HannAppTheme {
        NutrimentLogContent(
            modifier = Modifier,
            uiState = NutritionUiState(),
            pagingItems = flowOf(PagingData.from(listOf(Nutrition()))).collectAsLazyPagingItems(),
            onAdd = {},
            onClickBoxClick = {},
            navController = rememberNavController(),
            onItemSelected = {}
        )
    }
}
