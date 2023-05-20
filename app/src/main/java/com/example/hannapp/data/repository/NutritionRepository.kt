package com.example.hannapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.hannapp.data.database.dao.NutritionDao
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.model.entity.Nutrition
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NutritionRepository @Inject constructor(
    private val nutritionDao: NutritionDao,
) {
    suspend fun insert(nutrition: Nutrition) = nutritionDao.insert(nutrition)

    suspend fun get(id: Int) = nutritionDao.getById(id)

    fun getAll() = Pager(
        PagingConfig(pageSize = 24)
    ) {
        nutritionDao.getAll()
    }.flow.map { nutritionPagingData ->
        nutritionPagingData.map { NutritionConverter.entity(it).toUiModel() }
    }

    fun getFood() = nutritionDao.getFood()

    suspend fun update(nutritionUiModel: NutritionUiModel) = nutritionDao.update(
            NutritionConverter.uiModel(nutritionUiModel = nutritionUiModel).toEntity()
        ) == 1

    suspend fun delete(nutritionUiModel: NutritionUiModel) = nutritionDao.delete(
        NutritionConverter.uiModel(nutritionUiModel = nutritionUiModel).toEntity()
    )
}
