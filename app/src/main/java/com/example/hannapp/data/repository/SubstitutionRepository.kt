package com.example.hannapp.data.repository

import com.example.hannapp.data.model.NutritionModel
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SubstitutionRepository @Inject constructor() {
    fun getMaltoDextrin() = flowOf(NutritionModel(kcal = 382.0)) // TODO("introduce data source for maltodextrin nutriments")
}
