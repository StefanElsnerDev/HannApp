package com.example.hannapp.ui.input

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    isError: Boolean = false,
    supportingText: String = "",
    keyboardType: KeyboardType = KeyboardType.Number,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label) },
        supportingText = { Text(text = supportingText) },
        isError = isError,
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions
    )
}

@Preview()
@Composable
fun QuantityInput_LightMode() {
    HannAppTheme {
        InputField(
            value = "1234.5",
            onValueChange = {},
            modifier = Modifier,
            label = "Quantity"
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun QuantityInput_DarkMode() {
    HannAppTheme {
        InputField(
            value = "1234.5",
            onValueChange = {},
            modifier = Modifier,
            label = "Quantity"
        )
    }
}
