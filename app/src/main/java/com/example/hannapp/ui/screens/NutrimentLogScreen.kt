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
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.ui.components.AppScaffold
import com.example.hannapp.ui.components.NavigationBar
import com.example.hannapp.ui.history.NutrimentHistoryContent
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.output.CalculationContent
import com.example.hannapp.ui.selection.SelectionContent
import com.example.hannapp.ui.theme.Constraints.PADDING
import com.example.hannapp.ui.theme.Constraints.SPACE_VERTICAL
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutritionSelectViewModel
import com.example.hannapp.ui.viewmodel.NutritionUiState
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutrimentLogContent(
    modifier: Modifier,
    uiState: NutritionUiState,
    pagingItems: LazyPagingItems<NutritionUiModel>,
    loggedNutriments: List<NutrimentUiLogModel>,
    onAdd: (String) -> Unit,
    navController: NavHostController,
    onClickBoxClick: () -> Unit,
    onItemSelected: (NutritionUiModel) -> Unit
) {
    val snackBarHost = remember { SnackbarHostState() }

    AppScaffold(
        bottomBar = { NavigationBar(navController) },
        snackBarHost = { SnackbarHost(hostState = snackBarHost) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = PADDING)
            ) {
                SelectionContent(
                    modifier = Modifier.fillMaxWidth(),
                    uiState,
                    snackBarHost,
                    onClickBoxClick,
                    pagingItems,
                    onItemSelected
                )

                Spacer(modifier = Modifier.height(SPACE_VERTICAL))

                NutrimentHistoryContent(
                    modifier = Modifier.fillMaxWidth(),
                    nutriments = loggedNutriments
                )
            }

            CalculationContent(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = PADDING),
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
    val logged by viewModel.nutrimentLog.collectAsStateWithLifecycle()

    NutrimentLogContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        pagingItems = nutriments,
        loggedNutriments = logged,
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
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            loggedNutriments =
            listOf(
                NutrimentUiLogModel(
                    nutrition = NutritionUiModel(
                        name = "Peach"
                    ),
                    quantity = 123.4,
                    unit = "g",
                    timeStamp = 1681801313
                ),
                NutrimentUiLogModel(
                    nutrition = NutritionUiModel(
                        name = "Apple"
                    ),
                    quantity = 123.4,
                    unit = "g",
                    timeStamp = 1681801313
                ),
                NutrimentUiLogModel(
                    nutrition = NutritionUiModel(
                        name = "Chocolate"
                    ),
                    quantity = 123.4,
                    unit = "g",
                    timeStamp = 1681801313
                )
            ),
            onAdd = {},
            onClickBoxClick = {},
            navController = rememberNavController(),
            onItemSelected = {}
        )
    }
}
