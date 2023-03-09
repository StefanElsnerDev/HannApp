package com.example.hannapp.ui.selection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    items: List<String> = List(100){it.toString()}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    HannAppTheme {
        Box(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            OutlinedTextField(
                value = items[selectedIndex],
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

//        if (expanded) {

//        }
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
