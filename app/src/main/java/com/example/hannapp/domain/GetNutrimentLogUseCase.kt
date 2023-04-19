package com.example.hannapp.domain

import com.example.hannapp.data.model.NutrimentLogModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNutrimentLogUseCase @Inject constructor() {
    fun observeNutrimentLog(): Flow<List<NutrimentLogModel>>{
        TODO()
    }
}
