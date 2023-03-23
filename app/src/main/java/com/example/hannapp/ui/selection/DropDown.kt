package com.example.hannapp.ui.selection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.hannapp.ui.theme.HannAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DropDownField(
    modifier: Modifier = Modifier,
    items: List<String> = List(1000){it.toString()},
    onItemSelected: (Int) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    HannAppTheme {
        Box(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            OutlinedTextField(
                value = if (items.isNotEmpty()) items[selectedIndex] else "No Data",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                readOnly = true,
                textStyle = MaterialTheme.typography.titleMedium,
                label = { Text(text = "Auswahl") },
                trailingIcon = {
                    if (!expanded) Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                },
            )

            //ClickBox
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { expanded = true },
                color = Color.Transparent,
            ) {}

            if (expanded && items.isNotEmpty()) {
                DropDownList(
                    items = items,
                    onItemSelected = {
                        onItemSelected(it)
                        selectedIndex = it
                        expanded = false
                    },
                    onDismiss = { expanded = false }
                )
            }
        }
    }
}

@Preview(showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DropDownList(
    modifier: Modifier = Modifier,
    items: List<String> = List(100) { it.toString() },
    onItemSelected: (Int) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        HannAppTheme() {
            Surface(
                shape = RoundedCornerShape(12.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    itemsIndexed(items) { index, item ->
                        //Text(text = item)
                        DropDownItem(index = index, item = item) {
                            onItemSelected(index)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DropDownItem(
    modifier: Modifier = Modifier,
    index: Int = 0,
    item: String? = "Item",
    onClick: (Int) -> Unit = {}
) {
    HannAppTheme {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 4.dp)
                .clickable { onClick(index) },
        ) {
            Text(
                text = item ?: "empty",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

//            Dialog(onDismissRequest = { expanded = false }) {
//                val listState = rememberLazyListState()
//                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
////                    if (notSetLabel != null) {
////                        item {
////                            LargeDropdownMenuItem(
////                                text = notSetLabel,
////                                selected = false,
////                                enabled = false,
////                                onClick = { },
////                            )
////                        }
////                    }
//                    itemsIndexed(dings) { index, item ->
//                        Text(text = item)
//                        //val selectedItem = index == selectedIndex
//                    }
//                }
//            }


//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            items.forEachIndexed { index, s ->
//                DropdownMenuItem(
//                    onClick = {
//                        selectedIndex = index
//                        expanded = false
//                    },
//                    text = { Text(
//                        style = MaterialTheme.typography.titleMedium,
//                        text = s
//                    ) })
//            }
//        }
