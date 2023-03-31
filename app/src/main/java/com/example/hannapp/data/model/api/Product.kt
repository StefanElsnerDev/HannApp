package com.example.hannapp.data.model.api

import com.google.gson.annotations.SerializedName

data class Product(
    val code: String?,
    @SerializedName("product_name") val productName: String,
    val nutriments: Nutriments
)
