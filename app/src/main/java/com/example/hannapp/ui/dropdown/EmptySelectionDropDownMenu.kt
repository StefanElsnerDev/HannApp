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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.ui.theme.HannAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptySelectionDropDownMenu(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        OutlinedTextField(
            value = stringResource(id = R.string.nothing_selected),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            readOnly = true,
            textStyle = MaterialTheme.typography.titleMedium,
            label = { Text(text = stringResource(id = R.string.selection)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
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
        EmptySelectionDropDownMenu(
            modifier = Modifier
        ) {}
    }
}
