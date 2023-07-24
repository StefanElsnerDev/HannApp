package com.example.hannapp.ui.dropdown

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hannapp.ui.theme.HannAppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun <T : Any> DropDownDialog(
    pagingItems: LazyPagingItems<T>,
    onDismiss: () -> Unit,
    itemContent:
        @Composable()
        ((T) -> Unit)
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let { itemContent(it) }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DropDownList_LightMode() {
    HannAppTheme() {
        DropDownDialog(
            pagingItems = flowOf(PagingData.from(List(25) { it.toString() })).collectAsLazyPagingItems(),
            onDismiss = {}
        ) {
            SimpleDropDownItem(
                modifier = Modifier.fillMaxSize(),
                item = it
            )
        }
    }
}
