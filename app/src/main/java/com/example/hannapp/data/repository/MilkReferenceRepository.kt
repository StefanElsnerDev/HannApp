package com.example.hannapp.data.repository

import com.example.hannapp.data.model.MilkReferenceModel
import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.source.MilkReferenceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MilkReferenceRepository @Inject constructor(
    private val milkReferenceDataSource: MilkReferenceDataSource
) {
    suspend fun save(milkReferenceModel: MilkReferenceModel): Int {
        val quantityMax = milkReferenceModel.maxQuantity
        val dayTimeQuantity = milkReferenceModel.dayTimeQuantity
        val preNightQuantity = milkReferenceModel.preNightQuantity
        val nightQuantity = milkReferenceModel.nightQuantity

        require(quantityMax != null)
        require(dayTimeQuantity != null)
        require(preNightQuantity != null)
        require(nightQuantity != null)

        return milkReferenceDataSource.save(
            milkReferenceModel.copy(
                maxQuantity = quantityMax,
                dayTimeQuantity = dayTimeQuantity,
                preNightQuantity = preNightQuantity,
                nightQuantity = nightQuantity
            )
        )
    }

    fun emitReference(): Flow<MilkReferenceUiModel> =
        milkReferenceDataSource.emitReferences().map {
            require(it.maxQuantity != null)
            require(it.dayTimeQuantity != null)
            require(it.preNightQuantity != null)
            require(it.nightQuantity != null)

            MilkReferenceUiModel(
                maxQuantity = it.maxQuantity,
                dayTimeQuantity = it.dayTimeQuantity,
                preNightQuantity = it.preNightQuantity,
                nightQuantity = it.nightQuantity
            )
        }
}
