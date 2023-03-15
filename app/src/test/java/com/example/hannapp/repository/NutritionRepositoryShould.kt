package com.example.hannapp.repository

import com.example.hannapp.data.database.dao.NutritionDao
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.NutritionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionRepositoryShould {

    lateinit var nutritionRepository: NutritionRepository
    private val nutritionDao: NutritionDao = mock()
    private val nutrition = Nutrition()

    @BeforeEach
    fun beforeEach() {
        nutritionRepository = NutritionRepository(
            nutritionDao
        )
    }

    @Test
    fun insertNutritionData() = runTest {
        nutritionRepository.insert(nutrition)

        verify(nutritionDao).insert(any())
    }

    @Test
    fun getNutrition() = runTest {
        whenever(nutritionDao.getByName(any())).thenReturn(
            flowOf(nutrition)
        )

        val result = nutritionRepository.get("Some delicious food").first()

        Assertions.assertEquals(nutrition, result)
    }

    @Test
    fun getNutritionNames() = runTest {
        val names = listOf("Apple", "Banana", "Grapefruit")
        whenever(nutritionDao.getNames()).thenReturn(
            flowOf(names)
        )

        val result = nutritionRepository.getNames().first()

        Assertions.assertEquals(names, result)
    }

    @Test
    fun updateNutrition() = runTest {
        nutritionRepository.update(nutrition)

        verify(nutritionDao).update(any())
    }

    @Test
    fun deleteNutrition() = runTest {
        nutritionRepository.delete(nutrition)

        verify(nutritionDao).delete(any())
    }
}
