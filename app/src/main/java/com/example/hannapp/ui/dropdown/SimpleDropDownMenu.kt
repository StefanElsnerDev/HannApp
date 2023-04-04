package com.example.hannapp.ui.dropdown

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.ui.theme.HannAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropDownMenu(
    modifier: Modifier = Modifier,
    selected: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        OutlinedTextField(
            value = selected.ifBlank { "No Data" },
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            readOnly = true,
            textStyle = MaterialTheme.typography.titleMedium,
            label = { Text(text = "Auswahl") },
            trailingIcon = {
                if (!isExpanded) Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        )
        //ClickBox
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() },
            color = Color.Transparent,
        ) {}
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun DropDownField_LightMode() {
    HannAppTheme {
        SimpleDropDownMenu(
            modifier = Modifier,
            selected = NutritionModel().toString(),
            isExpanded = false
        ) {}
    }
}
