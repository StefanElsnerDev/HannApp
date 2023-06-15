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
    suspend fun save(milkReferenceUiModel: MilkReferenceUiModel): Int =
        milkReferenceDataSource.save(
            milkReferenceUiModel.let {
                MilkReferenceModel(
                    maxQuantity = it.maxQuantity,
                    dayTimeQuantity = it.dayTimeQuantity,
                    preNightQuantity = it.preNightQuantity,
                    nightQuantity = it.nightQuantity
                )
            }
        )

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
