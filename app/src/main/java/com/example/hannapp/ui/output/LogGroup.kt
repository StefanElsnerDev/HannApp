package com.example.hannapp.ui.output

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.ui.history.NutrimentHistoryContent
import com.example.hannapp.ui.selection.SelectionContent
import com.example.hannapp.ui.theme.Constraints
import com.example.hannapp.ui.theme.HannAppTheme
import com.example.hannapp.ui.viewmodel.NutrimentSelectContract
import kotlinx.coroutines.flow.flowOf

@Composable
fun LogGroup(
    modifier: Modifier,
    uiState: NutrimentSelectContract.State,
    event: (NutrimentSelectContract.Event) -> Unit,
    quantity: String,
    focusManager: FocusManager,
    selectedNutriment: NutritionUiModel,
    pagingItems: LazyPagingItems<NutritionUiModel>,
    loggedNutriments: List<NutrimentUiLogModel>
) {
    Column(
        modifier = modifier
            .padding(horizontal = Constraints.PADDING)
    ) {
        SelectionContent(
            modifier = Modifier.fillMaxWidth(),
            uiState = uiState,
            event = event,
            quantity = quantity,
            onQuantityChanged = { event(NutrimentSelectContract.Event.OnSetQuantity(it)) },
            onQuantityEntered = {
                focusManager.clearFocus()
                event(NutrimentSelectContract.Event.OnAdd)
            },
            selectedNutriment = selectedNutriment,
            onNutrimentChanged = { event(NutrimentSelectContract.Event.OnSelect(it)) },
            pagingItems = pagingItems
        )

        Spacer(modifier = Modifier.height(Constraints.SPACE_VERTICAL))

        NutrimentHistoryContent(
            modifier = Modifier.fillMaxWidth(),
            nutriments = loggedNutriments,
            onLongClick = {
                event(NutrimentSelectContract.Event.OnEdit(it))
                event(NutrimentSelectContract.Event.OnSetQuantity(it.quantity.toString()))
            }
        )
    }
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
        modifiedAt = 1999801313
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
        LogGroup(
            modifier = Modifier,
            uiState = NutrimentSelectContract.State(),
            event = {},
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            loggedNutriments = dummyList,
            quantity = "12.34",
            selectedNutriment = NutritionUiModel(),
            focusManager = LocalFocusManager.current
        )
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
fun NutrimentLogScreen_Compact_LightMode() {
    HannAppTheme {
        LogGroup(
            modifier = Modifier,
            uiState = NutrimentSelectContract.State(),
            event = {},
            pagingItems = flowOf(PagingData.from(listOf(NutritionUiModel()))).collectAsLazyPagingItems(),
            loggedNutriments = dummyList,
            quantity = "12.34",
            selectedNutriment = NutritionUiModel(),
            focusManager = LocalFocusManager.current
        )
    }
}
