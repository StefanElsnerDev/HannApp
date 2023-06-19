package com.example.hannapp.domain

import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.data.repository.MilkReferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveMilkQuantityReferencesUseCase @Inject constructor(
    private val milkReferenceRepository: MilkReferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(milkLimitReferenceUiModel: MilkLimitReferenceUiModel) {
        withContext(dispatcher) {
            milkReferenceRepository.save(
                milkLimitReferenceUiModel.let {
                    MilkReferenceUiModel(
                        maxQuantity = it.total.toFloat(),
                        dayTimeQuantity = it.day.toFloat(),
                        preNightQuantity = it.preNight.toFloat(),
                        nightQuantity = it.night.toFloat()
                    )
                }
            )
        }
    }
}
