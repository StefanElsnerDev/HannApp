package com.example.hannapp.ui.selection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString

@Composable
fun DropDown(items: List<String> = listOf("A", "B", "C", "D", "E", "F"),
             modifier: Modifier
) {
    var expanded by rememberSaveable{ mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Box(modifier = modifier) {
        ClickableText(
            text = AnnotatedString(items[selectedIndex]),
            modifier = Modifier.fillMaxWidth().align(Alignment.Center))
        {
            expanded = true
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expanded = false
                    },
                    text = { Text(text = s) })
            }
        }
    }
}
