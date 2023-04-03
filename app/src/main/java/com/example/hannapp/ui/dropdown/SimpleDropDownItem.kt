package com.example.hannapp.ui.dropdown

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.ui.theme.HannAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> SimpleDropDownItem(
    modifier: Modifier = Modifier,
    item: T,
    onClick: (T) -> Unit = {},
    onLongClick: (T) -> Unit = {}
) {
    HannAppTheme {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 4.dp)
                .combinedClickable(
                    onClick = { onClick(item) },
                    onLongClick = { onLongClick(item) }
                )
        ) {
            Text(
                text = item.toString(),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SimpleDropDownItem_LightMode() {
    HannAppTheme {
        SimpleDropDownItem(
            item = NutritionModel(name = "Apple"),
            onClick = {},
            onLongClick = {}
        )
    }
}
