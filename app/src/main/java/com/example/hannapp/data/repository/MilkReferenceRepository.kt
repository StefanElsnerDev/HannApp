package com.example.hannapp.data.repository

import com.example.hannapp.data.model.MilkReferenceModel
import com.example.hannapp.data.source.MilkReferenceDataSource
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

    fun emitReference() = milkReferenceDataSource.emitReferences()
}
