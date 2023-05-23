package com.example.hannapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.hannapp.R
import com.example.hannapp.data.model.api.Nutriments
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.ui.theme.HannAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onItemClick: (Product) -> Unit
) {
    Surface(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clickable { onItemClick(product) },
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = if (product.image.isNullOrBlank()) {
                painterResource(id = R.drawable.food)
            } else {
                rememberAsyncImagePainter(
                    model = product.image
                )
            }

            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .padding(12.dp),
                painter = painter,
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = product.productName
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "${product.nutriments.protein} g",
                    onValueChange = { _ -> },
                    label = {
                        Text(
                            text = "Protein",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    textStyle = MaterialTheme.typography.labelMedium,
                    enabled = false
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "${product.nutriments.fat} g",
                    onValueChange = { _ -> },
                    label = {
                        Text(
                            text = "Fat",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    textStyle = MaterialTheme.typography.labelMedium,
                    enabled = false
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "${product.nutriments.kcal} kcal",
                    onValueChange = { _ -> },
                    label = {
                        Text(
                            text = "Energy",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    textStyle = MaterialTheme.typography.labelMedium,
                    enabled = false
                )
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ProductCard_LightMode() {
    HannAppTheme {
        ProductCard(
            modifier = Modifier,
            product = Product(
                code = "124",
                productName = "Cola",
                nutriments = Nutriments(
                    1.1,
                    2.2,
                    3.3,
                    4.4,
                    5.5,
                    6.6,
                    7.7
                ),
                image = ""
            ),
            onItemClick = {}
        )
    }
}
