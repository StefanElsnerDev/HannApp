package com.example.hannapp.domain

import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.data.repository.MilkReferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMilkQuantityReferencesUseCase @Inject constructor(
    private val milkReferenceRepository: MilkReferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<MilkLimitReferenceUiModel> =
        milkReferenceRepository.emitReference().map {
            MilkLimitReferenceUiModel(
                total = it.maxQuantity.toString(),
                day = it.dayTimeQuantity.toString(),
                preNight = it.preNightQuantity.toString(),
                night = it.nightQuantity.toString()
            )
        }.flowOn(dispatcher)
}
