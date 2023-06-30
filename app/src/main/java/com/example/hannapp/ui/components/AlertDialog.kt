package com.example.hannapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hannapp.R
import com.example.hannapp.ui.theme.HannAppTheme

@Composable
fun Dialog(
    title: String,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                content = {
                    Text(text = stringResource(id = R.string.okay))
                }
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                content = {
                    Text(text = stringResource(id = R.string.abort))
                }
            )
        },
        icon = {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        shape = MaterialTheme.shapes.medium
    )
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Composable
fun AlertDialog_LightMode() {
    HannAppTheme {
        Dialog(
            title = "Dialog",
            text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.",
            onDismiss = {},
            onConfirm = {}
        )
    }
}
