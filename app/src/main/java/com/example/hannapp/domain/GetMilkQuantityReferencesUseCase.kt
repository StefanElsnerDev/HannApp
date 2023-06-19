package com.example.hannapp.domain

import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMilkQuantityReferencesUseCase @Inject constructor() {
    operator fun invoke(): Flow<MilkLimitReferenceUiModel> = flowOf()
}
