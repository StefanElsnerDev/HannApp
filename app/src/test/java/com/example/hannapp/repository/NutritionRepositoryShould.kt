package com.example.hannapp.repository

import com.example.hannapp.data.database.dao.NutritionBMIDao
import com.example.hannapp.data.model.entity.NutritionBMI
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
    private val nutritionBMIDao: NutritionBMIDao = mock()
    private val nutritionBMI = NutritionBMI()

    @BeforeEach
    fun beforeEach() {
        nutritionRepository = NutritionRepository(
            nutritionBMIDao
        )
    }

    @Test
    fun insertNutritionData() = runTest {
        nutritionRepository.insert(nutritionBMI)

        verify(nutritionBMIDao).insert(any())
    }

    @Test
    fun getNutritionBMI() = runTest {
        whenever(nutritionBMIDao.getByName(any())).thenReturn(
            flowOf(nutritionBMI)
        )

        val result = nutritionRepository.get("Some delicious food").first()

        Assertions.assertEquals(nutritionBMI, result)
    }

    @Test
    fun getNutritionNames() = runTest {
        val names = listOf("Apple", "Banana", "Grapefruit")
        whenever(nutritionBMIDao.getNames()).thenReturn(
            flowOf(names)
        )

        val result = nutritionRepository.getNames().first()

        Assertions.assertEquals(names, result)
    }

    @Test
    fun updateNutritionBMI() = runTest {
        nutritionRepository.update(nutritionBMI)

        verify(nutritionBMIDao).update(any())
    }

    @Test
    fun deleteNutritionBMI() = runTest {
        nutritionRepository.delete(nutritionBMI)

        verify(nutritionBMIDao).delete(any())
    }
}
