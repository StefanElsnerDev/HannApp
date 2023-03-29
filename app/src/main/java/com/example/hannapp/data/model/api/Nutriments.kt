package com.example.hannapp.data.model.api

import com.google.gson.annotations.SerializedName

data class Nutriments(
    @SerializedName("energy-kcal_100g") val kcal: Double?,
    @SerializedName("proteins_100g") val protein: Double?,
    @SerializedName("fat_100g") val fat: Double?,
    @SerializedName("carbohydrates_100g") val carbohydrates: Double?,
    @SerializedName("sugars_100g") val sugar: Double?,
    @SerializedName("fiber_100g") val fiber: Double?,
    @SerializedName("alcohol_100g") val alcohol: Double?
)
