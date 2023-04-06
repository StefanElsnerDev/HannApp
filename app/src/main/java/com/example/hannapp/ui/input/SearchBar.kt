package com.example.hannapp.ui.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannapp.R
import com.example.hannapp.ui.theme.HannAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier, onSearch: (String) -> Unit
) {
    var searchString by remember { mutableStateOf(TextFieldValue("")) }

    TextField(modifier = modifier
        .height(56.dp)
        .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions { onSearch(searchString.text) },
        singleLine = true,
        shape = RoundedCornerShape(percent = 25),
        value = searchString,
        onValueChange = { searchString = it },
        leadingIcon = {
            IconButton(onClick = { onSearch(searchString.text) },
                content = { Icon(Icons.Filled.Search, null) })
        },
        trailingIcon = {
            IconButton(onClick = { searchString = TextFieldValue("") },
                content = { Icon(Icons.Default.Clear, null) })
        },
        placeholder = { Text(text = stringResource(id = R.string.search)) })
}

@Preview
@Composable
fun SearchBar_Preview() {
    HannAppTheme {
        SearchBar(modifier = Modifier, onSearch = {})
    }
}
