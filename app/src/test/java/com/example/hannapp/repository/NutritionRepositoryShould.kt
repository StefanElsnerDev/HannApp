package com.example.hannapp.repository

import com.example.hannapp.data.database.dao.NutritionDao
import com.example.hannapp.data.model.Food
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.repository.NutritionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionRepositoryShould {

    lateinit var nutritionRepository: NutritionRepository
    private val nutritionDao: NutritionDao = mock()
    private val nutritionUiModel = NutritionUiModel()

    @BeforeEach
    fun beforeEach() {
        nutritionRepository = NutritionRepository(
            nutritionDao
        )
    }

    @Test
    fun insertNutritionData() = runTest {
        nutritionRepository.insert(Nutrition())

        verify(nutritionDao).insert(any())
    }

    @Test
    fun getNutrition() = runTest {
        whenever(nutritionDao.getById(any())).thenReturn(
            Nutrition(name = "Some delicious food")
        )

        val result = nutritionRepository.get(1)

        Assertions.assertEquals(Nutrition(name = "Some delicious food"), result)
    }

    @Test
    fun getFoodWithNamesAndID() = runTest {
        val foodList = listOf(Food(1, "Apple"), Food(2, "Banana"),Food(3, "Grapefruit"))
        whenever(nutritionDao.getFood()).thenReturn(
            flowOf(foodList)
        )

        val result = nutritionRepository.getFood().first()

        Assertions.assertEquals(foodList, result)
    }

    @Nested
    inner class Update{

        @BeforeEach
        fun beforeEach() = runTest {
            whenever(nutritionDao.update(any())).thenReturn(1)
        }


        @Test
        fun invokeUpdate() = runTest {
            nutritionRepository.update(nutritionUiModel)

            verify(nutritionDao).update(any())
        }

        @Test
        fun returnSuccessfulUpdate() = runTest {
            whenever(nutritionDao.update(any())).thenReturn(1)

            val result = nutritionRepository.update(nutritionUiModel)

            Assertions.assertEquals(true, result)
        }

        @Test
        fun returnFailingUpdate() = runTest {
            whenever(nutritionDao.update(any())).thenReturn(-1)

            val result = nutritionRepository.update(nutritionUiModel)

            Assertions.assertEquals(false, result)
        }
    }


    @Test
    fun deleteNutrition() = runTest {
        nutritionRepository.delete(nutritionUiModel)

        verify(nutritionDao).delete(any())
    }
}
